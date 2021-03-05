package org.jeecg.modules.mqtt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.mqtt.entity.*;
import org.jeecg.modules.mqtt.service.*;
import org.jeecg.modules.mqtt.vo.MbpMainplanabstractPage;
import org.jeecg.modules.mqtt.vo.PlanBOMVO;
import org.jeecg.modules.mqtt.vo.ScheduleVO;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
* @Description: 生产计划
* @Author: jeecg-boot
* @Date:   2020-08-25
* @Version: V1.0
*/
@Api(tags="生产计划")
@RestController
@RequestMapping("/system/mbpMainplanabstract")
@Slf4j
public class MbpMainplanabstractController {
   @Autowired
   private IMbpMainplanabstractService mbpMainplanabstractService;
   @Autowired
   private IMbpMainplanService mbpMainplanService;
   @Autowired
   private IMbpProductMapService mbpProductMapService;

   @Autowired
   private IMbpMaterialService iMbpMaterialService;

   @Autowired
   private IMbpEquipmentService iMbpEquipmentService;
   @Autowired
   private IMbpProductMapService iMbpProductMapService;
   @Autowired
   private IMbpTechnologicalDetailsService iMbpTechnologicalDetailsService;


    /**
     * 计划明细，分页列表查询
     *
     * @param mbpMainplan
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "生产计划-计划明细，分页列表查询")
    @ApiOperation(value="生产计划-分页列表查询", notes="生产计划-计划明细，分页列表查询")
    @GetMapping(value = "/listPlan")
    public Result<?> queryPlanPageList(MbpMainplan mbpMainplan,
                                       @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                       @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                       HttpServletRequest req) {
        QueryWrapper<MbpMainplan> queryWrapper = QueryGenerator.initQueryWrapper(mbpMainplan, req.getParameterMap());
        Page<MbpMainplan> page = new Page<MbpMainplan>(pageNo, pageSize);
        IPage<MbpMainplan> pageList = mbpMainplanService.page(page, queryWrapper);
        //批量查询计划明细中计划摘要中订单标题
        //step.1 先拿到全部的 ids
        //step.2 通过 ids，一次性查询计划明细中计划摘要中订单标题
        List<String> ids = pageList.getRecords().stream().map(MbpMainplan::getId).collect(Collectors.toList());
        if(ids!=null && ids.size()>0){
            Map<String,String> names = mbpMainplanService.getOrderTitleByIds(ids);
            pageList.getRecords().forEach(item->{
                item.setOrderTitle(names.get(item.getId()));
            });
        }
        return Result.ok(pageList);
    }

    /**
     * 查看计划明细进度
     * @param
     * @return
     */
    @ApiOperation(value="生产计划-查看计划明细进度", notes="生产计划-查看计划明细进度")
    @GetMapping(value = "/lookSchedule")
    public Result<?> lookSchedule(MbpMainplan mbpMainplan) {
        MbpEquipment equipment = iMbpEquipmentService.getById(mbpMainplan.getEquipmentId()); // 设备详情
        MbpProductMap mbpProductMap = iMbpProductMapService.getById(mbpMainplan.getMaterialId()); //产品详情
        // 查询产品工艺详情
        List<MbpTechnologicalDetails> list = iMbpTechnologicalDetailsService.queryByEquipment(mbpProductMap.getTechnologicalId());

        MbpTechnologicalDetails details = new MbpTechnologicalDetails();//设备所属工序
        /*判定颜色*/
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProcessId().equals(equipment.getProcessId())) {
                details = (list.get(i));
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProcessOrder() < details.getProcessOrder()) {
                list.get(i).setColor("green");
            } else if (list.get(i).getProcessOrder().equals(details.getProcessOrder())){
                list.get(i).setColor("blue");
            } else {
                list.get(i).setColor("gray");
            }
        }

        ScheduleVO vo = new ScheduleVO();
        vo.setList(list);
        vo.setWorkprocessId(equipment.getProcessId());

        return Result.ok(vo);
    }

    /**
     * 判断订单是否指定过生产计划
     * @param
     * @return
     */
    @ApiOperation(value="生产计划-判断订单是否指定过生产计划", notes="生产计划-判断订单是否指定过生产计划")
    @GetMapping(value = "/countOrderIfSet")
    public Result<?> countOrderIfSet(MbpMainplanabstract mbpMainplanabstract, HttpServletRequest req) {
        QueryWrapper<MbpMainplanabstract> queryWrapper = QueryGenerator.initQueryWrapper(mbpMainplanabstract, req.getParameterMap());
        int count = mbpMainplanabstractService.count(queryWrapper);
        return Result.ok(count);
    }

    /**
     * 查询计划明细
     * @param mbpMainplan 计划明细
     * @return
     */
    //@AutoLog(value = "生产计划-根据朱主计划id查询明细详情")
    @ApiOperation(value="生产计划-查询计划明细", notes="生产计划-查询计划明细")
    @GetMapping(value = "/getByMainplan")
    public Result<?> getByMainplan(MbpMainplan mbpMainplan, HttpServletRequest req) {
        QueryWrapper<MbpMainplan> queryWrapper = QueryGenerator.initQueryWrapper(mbpMainplan, req.getParameterMap());
        List<MbpMainplan> pageList = mbpMainplanService.list(queryWrapper);
        if (pageList.size() > 0) {

            MbpProductMap byId = mbpProductMapService.getById(pageList.get(0).getMaterialId());

           pageList.get(0).setMaterialName(byId.getProductName());

            return Result.ok(pageList.get(0));
        } else {
            return Result.error("未找到对应数据");
        }
    }

    /**
     * 根据主计划id查询明细详情
     *
     * @param mbpMainplan
     * @return
     */
    //@AutoLog(value = "生产计划-根据朱主计划id查询明细详情")
    @ApiOperation(value="生产计划-根据主计划id查询明细详情", notes="生产计划-根据主计划id查询明细详情")
    @GetMapping(value = "/queryListByMainId")
    public Result<?> queryListByMainId(MbpMainplan mbpMainplan) {
        List<PlanBOMVO> pageList = mbpMainplanService.queryListByMainId(mbpMainplan);
        return Result.ok(pageList);
    }

   /**
    * 分页列表查询
    *
    * @param mbpMainplanabstract
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   //@AutoLog(value = "生产计划-分页列表查询")
   @ApiOperation(value="生产计划-分页列表查询", notes="生产计划-分页列表查询")
   @GetMapping(value = "/list")
   public Result<?> queryPageList(MbpMainplanabstract mbpMainplanabstract,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<MbpMainplanabstract> queryWrapper = QueryGenerator.initQueryWrapper(mbpMainplanabstract, req.getParameterMap());
       Page<MbpMainplanabstract> page = new Page<MbpMainplanabstract>(pageNo, pageSize);
       IPage<MbpMainplanabstract> pageList = mbpMainplanabstractService.page(page, queryWrapper);
       return Result.ok(pageList);
   }

   /**
    *   添加
    *
    * @param mbpMainplanabstractPage
    * @return
    */
   @AutoLog(value = "生产计划-添加")
   @ApiOperation(value="生产计划-添加", notes="生产计划-添加")
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody MbpMainplanabstractPage mbpMainplanabstractPage) {
       MbpMainplanabstract mbpMainplanabstract = new MbpMainplanabstract();
       BeanUtils.copyProperties(mbpMainplanabstractPage, mbpMainplanabstract);
       mbpMainplanabstractService.saveMain(mbpMainplanabstract, mbpMainplanabstractPage.getMbpMainplanList());
       return Result.ok("添加成功！");
   }

   /**
    *  编辑
    *
    * @param mbpMainplanabstractPage
    * @return
    */
   @AutoLog(value = "生产计划-编辑")
   @ApiOperation(value="生产计划-编辑", notes="生产计划-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody MbpMainplanabstractPage mbpMainplanabstractPage) {
       MbpMainplanabstract mbpMainplanabstract = new MbpMainplanabstract();
       BeanUtils.copyProperties(mbpMainplanabstractPage, mbpMainplanabstract);
       MbpMainplanabstract mbpMainplanabstractEntity = mbpMainplanabstractService.getById(mbpMainplanabstract.getId());
       if(mbpMainplanabstractEntity==null) {
           return Result.error("未找到对应数据");
       }
       mbpMainplanabstractService.updateMain(mbpMainplanabstract, mbpMainplanabstractPage.getMbpMainplanList());
       return Result.ok("编辑成功!");
   }

   /**
    *  确认开始生产任务
    *
    * @param mbpMainplan 计划详情id，设备编号
    * @return
    */
   @AutoLog(value = "生产计划-确认开始生产任务")
   @ApiOperation(value="生产计划-确认开始生产任务", notes="生产计划-确认开始生产任务")
   @PutMapping(value = "/handleOKStart")
   public Result<?> handleOKStart(@RequestBody MbpMainplan mbpMainplan) {

       QueryWrapper<MbpMainplan> queryWrapper = new QueryWrapper<>();
       queryWrapper.eq("planstatus_id", 1);
       queryWrapper.eq("equipment_id", mbpMainplan.getEquipmentId());
       int count = mbpMainplanService.count(queryWrapper);
       // log.info("count : " + count);
       Result<String> result = new Result<>();
       if (count == 0) {
           MbpMainplan plan = new MbpMainplan();
           plan.setId(mbpMainplan.getId());
           plan.setPlanstatusId(1);
           // log.info("update : " + plan);
           mbpMainplanService.updateById(plan);
           return result.success("任务开始成功");
       } else {
           return result.error500("任务开始失败，当前设备已有计划正在进行");
       }


   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @AutoLog(value = "生产计划-通过id删除")
   @ApiOperation(value="生产计划-通过id删除", notes="生产计划-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       mbpMainplanabstractService.delMain(id);
       return Result.ok("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @AutoLog(value = "生产计划-批量删除")
   @ApiOperation(value="生产计划-批量删除", notes="生产计划-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.mbpMainplanabstractService.delBatchMain(Arrays.asList(ids.split(",")));
       return Result.ok("批量删除成功！");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @AutoLog(value = "生产计划-通过id查询")
   @ApiOperation(value="生产计划-通过id查询", notes="生产计划-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       MbpMainplanabstract mbpMainplanabstract = mbpMainplanabstractService.getById(id);
       if(mbpMainplanabstract==null) {
           return Result.error("未找到对应数据");
       }
       return Result.ok(mbpMainplanabstract);

   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @AutoLog(value = "生产计划明细-通过主表ID查询")
   @ApiOperation(value="生产计划明细-通过主表ID查询", notes="生产计划明细-通过主表ID查询")
   @GetMapping(value = "/queryMbpMainplanByMainId")
   public Result<?> queryMbpMainplanListByMainId(@RequestParam(name="id",required=true) String id) {
       List<MbpMainplan> mbpMainplanList = mbpMainplanService.selectByMainId(id);
       IPage<MbpMainplan> page = new Page<>();
       page.setRecords(mbpMainplanList);
       page.setTotal(mbpMainplanList.size());
       return Result.ok(page);
   }

   /**
   * 导出excel
   *
   * @param request
   * @param mbpMainplanabstract
   */
   @RequestMapping(value = "/exportXls")
   public ModelAndView exportXls(HttpServletRequest request, MbpMainplanabstract mbpMainplanabstract) {
     // Step.1 组装查询条件查询数据
     QueryWrapper<MbpMainplanabstract> queryWrapper = QueryGenerator.initQueryWrapper(mbpMainplanabstract, request.getParameterMap());
     LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

     //Step.2 获取导出数据
     List<MbpMainplanabstract> queryList = mbpMainplanabstractService.list(queryWrapper);
     // 过滤选中数据
     String selections = request.getParameter("selections");
     List<MbpMainplanabstract> mbpMainplanabstractList = new ArrayList<MbpMainplanabstract>();
     if(oConvertUtils.isEmpty(selections)) {
         mbpMainplanabstractList = queryList;
     }else {
         List<String> selectionList = Arrays.asList(selections.split(","));
         mbpMainplanabstractList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
     }

     // Step.3 组装pageList
     List<MbpMainplanabstractPage> pageList = new ArrayList<MbpMainplanabstractPage>();
     for (MbpMainplanabstract main : mbpMainplanabstractList) {
         MbpMainplanabstractPage vo = new MbpMainplanabstractPage();
         BeanUtils.copyProperties(main, vo);
         List<MbpMainplan> mbpMainplanList = mbpMainplanService.selectByMainId(main.getId());
         vo.setMbpMainplanList(mbpMainplanList);
         pageList.add(vo);
     }

     // Step.4 AutoPoi 导出Excel
     ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
     mv.addObject(NormalExcelConstants.FILE_NAME, "生产计划列表");
     mv.addObject(NormalExcelConstants.CLASS, MbpMainplanabstractPage.class);
     mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("生产计划数据", "导出人:"+sysUser.getRealname(), "生产计划"));
     mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
     return mv;
   }

   /**
   * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
   @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
   public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
     MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
     Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
     for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
         MultipartFile file = entity.getValue();// 获取上传文件对象
         ImportParams params = new ImportParams();
         params.setTitleRows(2);
         params.setHeadRows(1);
         params.setNeedSave(true);
         try {
             List<MbpMainplanabstractPage> list = ExcelImportUtil.importExcel(file.getInputStream(), MbpMainplanabstractPage.class, params);
             for (MbpMainplanabstractPage page : list) {
                 MbpMainplanabstract po = new MbpMainplanabstract();
                 BeanUtils.copyProperties(page, po);
                 mbpMainplanabstractService.saveMain(po, page.getMbpMainplanList());
             }
             return Result.ok("文件导入成功！数据行数:" + list.size());
         } catch (Exception e) {
             log.error(e.getMessage(),e);
             return Result.error("文件导入失败:"+e.getMessage());
         } finally {
             try {
                 file.getInputStream().close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
     }
     return Result.ok("文件导入失败！");
   }

}
