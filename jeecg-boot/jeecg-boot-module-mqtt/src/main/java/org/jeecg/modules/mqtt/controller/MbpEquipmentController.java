package org.jeecg.modules.mqtt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.mqtt.entity.MbpEquipment;
import org.jeecg.modules.mqtt.service.IMbpEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
* @Description: 设备表
* @Author: jeecg-boot
* @Date:   2020-10-30
* @Version: V1.0
*/
@Api(tags="设备表")
@RestController
@RequestMapping("/system/mbpEquipment")
@Slf4j
public class MbpEquipmentController extends JeecgController<MbpEquipment, IMbpEquipmentService> {
   @Autowired
   private IMbpEquipmentService mbpEquipmentService;

   /**
    * 分页列表查询
    *
    * @param mbpEquipment
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   @AutoLog(value = "设备表-分页列表查询")
   @ApiOperation(value="设备表-分页列表查询", notes="设备表-分页列表查询")
   @GetMapping(value = "/list")
   public Result<?> queryPageList(MbpEquipment mbpEquipment,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<MbpEquipment> queryWrapper = QueryGenerator.initQueryWrapper(mbpEquipment, req.getParameterMap());
       Page<MbpEquipment> page = new Page<MbpEquipment>(pageNo, pageSize);
       IPage<MbpEquipment> pageList = mbpEquipmentService.page(page, queryWrapper);
       return Result.ok(pageList);
   }

   /**
    *   添加
    *
    * @param mbpEquipment
    * @return
    */
   @AutoLog(value = "设备表-添加")
   @ApiOperation(value="设备表-添加", notes="设备表-添加")
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody MbpEquipment mbpEquipment) {
       mbpEquipmentService.save(mbpEquipment);
       return Result.ok("添加成功！");
   }

   /**
    *  编辑
    *
    * @param mbpEquipment
    * @return
    */
   @AutoLog(value = "设备表-编辑")
   @ApiOperation(value="设备表-编辑", notes="设备表-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody MbpEquipment mbpEquipment) {
       mbpEquipmentService.updateById(mbpEquipment);
       return Result.ok("编辑成功!");
   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @AutoLog(value = "设备表-通过id删除")
   @ApiOperation(value="设备表-通过id删除", notes="设备表-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       mbpEquipmentService.removeById(id);
       return Result.ok("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @AutoLog(value = "设备表-批量删除")
   @ApiOperation(value="设备表-批量删除", notes="设备表-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.mbpEquipmentService.removeByIds(Arrays.asList(ids.split(",")));
       return Result.ok("批量删除成功!");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @AutoLog(value = "设备表-通过id查询")
   @ApiOperation(value="设备表-通过id查询", notes="设备表-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       MbpEquipment mbpEquipment = mbpEquipmentService.getById(id);
       if(mbpEquipment==null) {
           return Result.error("未找到对应数据");
       }
       return Result.ok(mbpEquipment);
   }

   /**
   * 导出excel
   *
   * @param request
   * @param mbpEquipment
   */
   @RequestMapping(value = "/exportXls")
   public ModelAndView exportXls(HttpServletRequest request, MbpEquipment mbpEquipment) {
       return super.exportXls(request, mbpEquipment, MbpEquipment.class, "设备表");
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
       return super.importExcel(request, response, MbpEquipment.class);
   }

}
