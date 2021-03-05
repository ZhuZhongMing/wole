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
import org.jeecg.modules.mqtt.entity.MbpCustomer;
import org.jeecg.modules.mqtt.entity.MbpMainplanabstract;
import org.jeecg.modules.mqtt.entity.MbpOrder;
import org.jeecg.modules.mqtt.entity.MbpOrderlist;
import org.jeecg.modules.mqtt.service.IMbpCustomerService;
import org.jeecg.modules.mqtt.service.IMbpMainplanabstractService;
import org.jeecg.modules.mqtt.service.IMbpOrderService;
import org.jeecg.modules.mqtt.service.IMbpOrderlistService;
import org.jeecg.modules.mqtt.vo.MbpOrderPage;
import org.jeecg.modules.mqtt.vo.PlanVO;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
* @Description: 订单
* @Author: jeecg-boot
* @Date:   2020-08-19
* @Version: V1.0
*/
@Api(tags="订单")
@RestController
@RequestMapping("/system/mbpOrder")
@Slf4j
public class MbpOrderController {
   @Autowired
   private IMbpOrderService mbpOrderService;
   @Autowired
   private IMbpOrderlistService mbpOrderlistService;
   @Autowired
   private IMbpCustomerService iMbpCustomerService;
   @Autowired
   private IMbpMainplanabstractService iMbpMainplanabstractService;



    /**
     * 统计订单量
     *
     * @param mbpOrder
     * @param req
     * @return
     */
    //@AutoLog(value = "订单-统计订单量")
    @ApiOperation(value="订单-统计订单量", notes="订单-统计订单量")
    @GetMapping(value = "/countOrder")
    public Result<?> countOrder(MbpOrder mbpOrder,
                                HttpServletRequest req) {
        QueryWrapper<MbpOrder> queryWrapper = QueryGenerator.initQueryWrapper(mbpOrder, req.getParameterMap());
        Integer count = mbpOrderService.count(queryWrapper);
        return Result.ok(count);
    }

    /**
     * 统计今日订单量
     *
     * @return
     */
    //@AutoLog(value = "订单-统计今日订单量")
    @ApiOperation(value="订单-统计今日订单量", notes="订单-统计今日订单量")
    @GetMapping(value = "/countTodayOrder")
    public Result<?> countTodayOrder() {
        QueryWrapper<MbpOrder> queryWrapper = new QueryWrapper<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String beginTime = sdf.format(date) + " 00:00:00:001";
        String endTime = sdf.format(date) + " 23:59:59:999";
        queryWrapper.ge("create_time", beginTime);
        queryWrapper.le("create_time", endTime);
        Integer count = mbpOrderService.count(queryWrapper);
        return Result.ok(count);
    }

   /**
    * 分页列表查询
    *
    * @param mbpOrder
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   //@AutoLog(value = "订单-分页列表查询")
   @ApiOperation(value="订单-分页列表查询", notes="订单-分页列表查询")
   @GetMapping(value = "/list")
   public Result<?> queryPageList(MbpOrder mbpOrder,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<MbpOrder> queryWrapper = QueryGenerator.initQueryWrapper(mbpOrder, req.getParameterMap());
       Page<MbpOrder> page = new Page<MbpOrder>(pageNo, pageSize);
       IPage<MbpOrder> pageList = mbpOrderService.page(page, queryWrapper);
       return Result.ok(pageList);
   }

    /**
     * 查询未指定生产计划的订单
     *
     * @return
     */
    //@AutoLog(value = "订单-查询未指定生产计划的订单")
    @ApiOperation(value="订单-查询未指定生产计划的订单", notes="订单-查询未指定生产计划的订单")
    @GetMapping(value = "/listNotInPlan")
    public Result<?> listNotInPlan() {
        List<MbpMainplanabstract> planList = iMbpMainplanabstractService.list();
        List<String> ids = planList.stream().map(MbpMainplanabstract::getOrderId).collect(Collectors.toList());
        QueryWrapper<MbpOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.notIn("id", ids);
        List<MbpOrder> list = mbpOrderService.list(queryWrapper);
        return Result.ok(list);
    }

   /**
    *   添加
    *
    * @param mbpOrderPage
    * @return
    */
   @AutoLog(value = "订单-添加")
   @ApiOperation(value="订单-添加", notes="订单-添加")
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody MbpOrderPage mbpOrderPage) {
       // 添加操作之前，获取当前时间记录 - 到毫秒
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
       String beforeSave = sdf.format(new Date());
       log.info("before : " + beforeSave);

       MbpOrder mbpOrder = new MbpOrder();
       BeanUtils.copyProperties(mbpOrderPage, mbpOrder);
       mbpOrderService.saveMain(mbpOrder, mbpOrderPage.getMbpOrderlistList());

       //添加成功将id查出返回至前端
       QueryWrapper<MbpOrder> queryWrapper = new QueryWrapper<>();
       queryWrapper.ge("create_time", beforeSave);
       List<MbpOrder> list = mbpOrderService.list(queryWrapper);
       MbpOrder oreder = new MbpOrder();
       oreder.setId(list.get(0).getId());
       Result<MbpOrder> result = new Result<>();
       result.setResult(oreder);
       result.setSuccess(true);
       result.setMessage("添加成功！");

       return result;
   }

   /**
    *  编辑
    *
    * @param mbpOrderPage
    * @return
    */
   @AutoLog(value = "订单-编辑")
   @ApiOperation(value="订单-编辑", notes="订单-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody MbpOrderPage mbpOrderPage) {
       MbpOrder mbpOrder = new MbpOrder();
       BeanUtils.copyProperties(mbpOrderPage, mbpOrder);
       MbpOrder mbpOrderEntity = mbpOrderService.getById(mbpOrder.getId());
       if(mbpOrderEntity==null) {
           return Result.error("未找到对应数据");
       }
       mbpOrderService.updateMain(mbpOrder, mbpOrderPage.getMbpOrderlistList());
       return Result.ok("编辑成功!");
   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @AutoLog(value = "订单-通过id删除")
   @ApiOperation(value="订单-通过id删除", notes="订单-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       mbpOrderService.delMain(id);
       return Result.ok("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @AutoLog(value = "订单-批量删除")
   @ApiOperation(value="订单-批量删除", notes="订单-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.mbpOrderService.delBatchMain(Arrays.asList(ids.split(",")));
       return Result.ok("批量删除成功！");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   //@AutoLog(value = "订单-通过id查询")
   @ApiOperation(value="订单-通过id查询", notes="订单-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       MbpOrder mbpOrder = mbpOrderService.getById(id);
       if(mbpOrder==null) {
           return Result.error("未找到对应数据");
       }
       MbpCustomer customer = iMbpCustomerService.getById(mbpOrder.getCustomerId());
       mbpOrder.setCustomerName(customer.getCustomerName());
       return Result.ok(mbpOrder);
   }

    /**
     * 订单明细-通过主表ID查询，生成对应的生产计划明细
     *
     * @param id
     * @return
     */
    @ApiOperation(value="订单明细-通过主表ID查询，生成对应的生产计划明细", notes="订单明细-通过主表ID查询，生成对应的生产计划明细")
    @GetMapping(value = "/queryListToPlan")
    public Result<?> queryListToPlan(@RequestParam(name="id",required=true) String id) {
        /*List<MbpOrderlist> mbpOrderlistList = mbpOrderlistService.selectByMainId(id);
        IPage <MbpOrderlist> page = new Page<>();
        page.setRecords(mbpOrderlistList);
        page.setTotal(mbpOrderlistList.size());*/
        List<PlanVO> list = mbpOrderlistService.queryListToPlan(id);
        return Result.ok(list);
    }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   //@AutoLog(value = "订单明细-通过主表ID查询")
   @ApiOperation(value="订单明细-通过主表ID查询", notes="订单明细-通过主表ID查询")
   @GetMapping(value = "/queryMbpOrderlistByMainId")
   public Result<?> queryMbpOrderlistListByMainId(@RequestParam(name="id",required=true) String id) {
       List<MbpOrderlist> mbpOrderlistList = mbpOrderlistService.selectByMainId(id);
       IPage<MbpOrderlist> page = new Page<>();
       page.setRecords(mbpOrderlistList);
       page.setTotal(mbpOrderlistList.size());
       return Result.ok(page);
   }

   /**
   * 导出excel
   *
   * @param request
   * @param mbpOrder
   */
   @RequestMapping(value = "/exportXls")
   public ModelAndView exportXls(HttpServletRequest request, MbpOrder mbpOrder) {
     // Step.1 组装查询条件查询数据
     QueryWrapper<MbpOrder> queryWrapper = QueryGenerator.initQueryWrapper(mbpOrder, request.getParameterMap());
     LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

     //Step.2 获取导出数据
     List<MbpOrder> queryList = mbpOrderService.list(queryWrapper);
     // 过滤选中数据
     String selections = request.getParameter("selections");
     List<MbpOrder> mbpOrderList = new ArrayList<MbpOrder>();
     if(oConvertUtils.isEmpty(selections)) {
         mbpOrderList = queryList;
     }else {
         List<String> selectionList = Arrays.asList(selections.split(","));
         mbpOrderList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
     }

     // Step.3 组装pageList
     List<MbpOrderPage> pageList = new ArrayList<MbpOrderPage>();
     for (MbpOrder main : mbpOrderList) {
         MbpOrderPage vo = new MbpOrderPage();
         BeanUtils.copyProperties(main, vo);
         List<MbpOrderlist> mbpOrderlistList = mbpOrderlistService.selectByMainId(main.getId());
         vo.setMbpOrderlistList(mbpOrderlistList);
         pageList.add(vo);
     }

     // Step.4 AutoPoi 导出Excel
     ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
     mv.addObject(NormalExcelConstants.FILE_NAME, "订单列表");
     mv.addObject(NormalExcelConstants.CLASS, MbpOrderPage.class);
     mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("订单数据", "导出人:"+sysUser.getRealname(), "订单"));
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
             List<MbpOrderPage> list = ExcelImportUtil.importExcel(file.getInputStream(), MbpOrderPage.class, params);
             for (MbpOrderPage page : list) {
                 MbpOrder po = new MbpOrder();
                 BeanUtils.copyProperties(page, po);
                 mbpOrderService.saveMain(po, page.getMbpOrderlistList());
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
