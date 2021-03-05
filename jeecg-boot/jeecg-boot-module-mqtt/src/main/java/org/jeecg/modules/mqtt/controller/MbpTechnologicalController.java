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
import org.jeecg.modules.mqtt.entity.MbpTechnological;
import org.jeecg.modules.mqtt.entity.MbpTechnologicalDetails;
import org.jeecg.modules.mqtt.service.IMbpTechnologicalDetailsService;
import org.jeecg.modules.mqtt.service.IMbpTechnologicalService;
import org.jeecg.modules.mqtt.vo.MbpTechnologicalPage;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @Description: 工艺信息
* @Author: jeecg-boot
* @Date:   2020-10-09
* @Version: V1.0
*/
@Api(tags="工艺信息")
@RestController
@RequestMapping("/system/mbpTechnological")
@Slf4j
public class MbpTechnologicalController {
   @Autowired
   private IMbpTechnologicalService mbpTechnologicalService;
   @Autowired
   private IMbpTechnologicalDetailsService mbpTechnologicalDetailsService;

   /**
    * 分页列表查询
    *
    * @param mbpTechnological
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   @AutoLog(value = "工艺信息-分页列表查询")
   @ApiOperation(value="工艺信息-分页列表查询", notes="工艺信息-分页列表查询")
   @GetMapping(value = "/list")
   public Result<?> queryPageList(MbpTechnological mbpTechnological,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<MbpTechnological> queryWrapper = QueryGenerator.initQueryWrapper(mbpTechnological, req.getParameterMap());
       Page<MbpTechnological> page = new Page<MbpTechnological>(pageNo, pageSize);
       IPage<MbpTechnological> pageList = mbpTechnologicalService.page(page, queryWrapper);
       return Result.ok(pageList);
   }

   /**
    *   添加
    *
    * @param mbpTechnologicalPage
    * @return
    */
   @AutoLog(value = "工艺信息-添加")
   @ApiOperation(value="工艺信息-添加", notes="工艺信息-添加")
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody MbpTechnologicalPage mbpTechnologicalPage) {
       MbpTechnological mbpTechnological = new MbpTechnological();
       BeanUtils.copyProperties(mbpTechnologicalPage, mbpTechnological);
       mbpTechnologicalService.saveMain(mbpTechnological, mbpTechnologicalPage.getMbpTechnologicalDetailsList());
       return Result.ok("添加成功！");
   }

   /**
    *  编辑
    *
    * @param mbpTechnologicalPage
    * @return
    */
   @AutoLog(value = "工艺信息-编辑")
   @ApiOperation(value="工艺信息-编辑", notes="工艺信息-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody MbpTechnologicalPage mbpTechnologicalPage) {
       MbpTechnological mbpTechnological = new MbpTechnological();
       BeanUtils.copyProperties(mbpTechnologicalPage, mbpTechnological);
       MbpTechnological mbpTechnologicalEntity = mbpTechnologicalService.getById(mbpTechnological.getId());
       if(mbpTechnologicalEntity==null) {
           return Result.error("未找到对应数据");
       }
       mbpTechnologicalService.updateMain(mbpTechnological, mbpTechnologicalPage.getMbpTechnologicalDetailsList());
       return Result.ok("编辑成功!");
   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @AutoLog(value = "工艺信息-通过id删除")
   @ApiOperation(value="工艺信息-通过id删除", notes="工艺信息-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       mbpTechnologicalService.delMain(id);
       return Result.ok("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @AutoLog(value = "工艺信息-批量删除")
   @ApiOperation(value="工艺信息-批量删除", notes="工艺信息-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.mbpTechnologicalService.delBatchMain(Arrays.asList(ids.split(",")));
       return Result.ok("批量删除成功！");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @AutoLog(value = "工艺信息-通过id查询")
   @ApiOperation(value="工艺信息-通过id查询", notes="工艺信息-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       MbpTechnological mbpTechnological = mbpTechnologicalService.getById(id);
       if(mbpTechnological==null) {
           return Result.error("未找到对应数据");
       }
       return Result.ok(mbpTechnological);

   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @AutoLog(value = "工艺详情-通过主表ID查询")
   @ApiOperation(value="工艺详情-通过主表ID查询", notes="工艺详情-通过主表ID查询")
   @GetMapping(value = "/queryMbpTechnologicalDetailsByMainId")
   public Result<?> queryMbpTechnologicalDetailsListByMainId(@RequestParam(name="id",required=true) String id) {
       List<MbpTechnologicalDetails> mbpTechnologicalDetailsList = mbpTechnologicalDetailsService.selectByMainId(id);
       IPage<MbpTechnologicalDetails> page = new Page<>();
       page.setRecords(mbpTechnologicalDetailsList);
       page.setTotal(mbpTechnologicalDetailsList.size());
       return Result.ok(page);
   }

   /**
   * 导出excel
   *
   * @param request
   * @param mbpTechnological
   */
   @RequestMapping(value = "/exportXls")
   public ModelAndView exportXls(HttpServletRequest request, MbpTechnological mbpTechnological) {
     // Step.1 组装查询条件查询数据
     QueryWrapper<MbpTechnological> queryWrapper = QueryGenerator.initQueryWrapper(mbpTechnological, request.getParameterMap());
     LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

     //Step.2 获取导出数据
     List<MbpTechnological> queryList = mbpTechnologicalService.list(queryWrapper);
     // 过滤选中数据
     String selections = request.getParameter("selections");
     List<MbpTechnological> mbpTechnologicalList = new ArrayList<MbpTechnological>();
     if(oConvertUtils.isEmpty(selections)) {
         mbpTechnologicalList = queryList;
     }else {
         List<String> selectionList = Arrays.asList(selections.split(","));
         mbpTechnologicalList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
     }

     // Step.3 组装pageList
     List<MbpTechnologicalPage> pageList = new ArrayList<MbpTechnologicalPage>();
     for (MbpTechnological main : mbpTechnologicalList) {
         MbpTechnologicalPage vo = new MbpTechnologicalPage();
         BeanUtils.copyProperties(main, vo);
         List<MbpTechnologicalDetails> mbpTechnologicalDetailsList = mbpTechnologicalDetailsService.selectByMainId(main.getId());
         vo.setMbpTechnologicalDetailsList(mbpTechnologicalDetailsList);
         pageList.add(vo);
     }

     // Step.4 AutoPoi 导出Excel
     ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
     mv.addObject(NormalExcelConstants.FILE_NAME, "工艺信息列表");
     mv.addObject(NormalExcelConstants.CLASS, MbpTechnologicalPage.class);
     mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("工艺信息数据", "导出人:"+sysUser.getRealname(), "工艺信息"));
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
             List<MbpTechnologicalPage> list = ExcelImportUtil.importExcel(file.getInputStream(), MbpTechnologicalPage.class, params);
             for (MbpTechnologicalPage page : list) {
                 MbpTechnological po = new MbpTechnological();
                 BeanUtils.copyProperties(page, po);
                 mbpTechnologicalService.saveMain(po, page.getMbpTechnologicalDetailsList());
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
