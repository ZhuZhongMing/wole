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
import org.jeecg.modules.mqtt.entity.MbpMaterial;
import org.jeecg.modules.mqtt.service.IMbpMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
* @Description: 物料表
* @Author: jeecg-boot
* @Date:   2020-08-17
* @Version: V1.0
*/
@Api(tags="物料表")
@RestController
@RequestMapping("/system/mbpMaterial")
@Slf4j
public class MbpMaterialController extends JeecgController<MbpMaterial, IMbpMaterialService> {
   @Autowired
   private IMbpMaterialService mbpMaterialService;

    /**
     * 全查询
     *
     * @param mbpMaterial
     * @param req
     * @return
     */
    //@AutoLog(value = "物料表-全查询")
    @ApiOperation(value="物料表-全查询", notes="物料表-全查询")
    @GetMapping(value = "/allList")
    public Result<?> allList(MbpMaterial mbpMaterial,
                             HttpServletRequest req) {
        QueryWrapper<MbpMaterial> queryWrapper = QueryGenerator.initQueryWrapper(mbpMaterial, req.getParameterMap());
        List<MbpMaterial> pageList = mbpMaterialService.list( queryWrapper);
        return Result.ok(pageList);
    }

   /**
    * 分页列表查询
    *
    * @param mbpMaterial
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   //@AutoLog(value = "物料表-分页列表查询")
   @ApiOperation(value="物料表-分页列表查询", notes="物料表-分页列表查询")
   @GetMapping(value = "/list")
   public Result<?> queryPageList(MbpMaterial mbpMaterial,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<MbpMaterial> queryWrapper = QueryGenerator.initQueryWrapper(mbpMaterial, req.getParameterMap());
       Page<MbpMaterial> page = new Page<MbpMaterial>(pageNo, pageSize);
       IPage<MbpMaterial> pageList = mbpMaterialService.page(page, queryWrapper);
       return Result.ok(pageList);
   }

   /**
    *   添加
    *
    * @param mbpMaterial
    * @return
    */
   @AutoLog(value = "物料表-添加")
   @ApiOperation(value="物料表-添加", notes="物料表-添加")
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody MbpMaterial mbpMaterial) {
       mbpMaterialService.save(mbpMaterial);
       return Result.ok("添加成功！");
   }

   /**
    *  编辑
    *
    * @param mbpMaterial
    * @return
    */
   @AutoLog(value = "物料表-编辑")
   @ApiOperation(value="物料表-编辑", notes="物料表-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody MbpMaterial mbpMaterial) {
       mbpMaterialService.updateById(mbpMaterial);
       return Result.ok("编辑成功!");
   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @AutoLog(value = "物料表-通过id删除")
   @ApiOperation(value="物料表-通过id删除", notes="物料表-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       mbpMaterialService.removeById(id);
       return Result.ok("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @AutoLog(value = "物料表-批量删除")
   @ApiOperation(value="物料表-批量删除", notes="物料表-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.mbpMaterialService.removeByIds(Arrays.asList(ids.split(",")));
       return Result.ok("批量删除成功!");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @AutoLog(value = "物料表-通过id查询")
   @ApiOperation(value="物料表-通过id查询", notes="物料表-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       MbpMaterial mbpMaterial = mbpMaterialService.getById(id);
       if(mbpMaterial==null) {
           return Result.error("未找到对应数据");
       }
       return Result.ok(mbpMaterial);
   }

   /**
   * 导出excel
   *
   * @param request
   * @param mbpMaterial
   */
   @RequestMapping(value = "/exportXls")
   public ModelAndView exportXls(HttpServletRequest request, MbpMaterial mbpMaterial) {
       return super.exportXls(request, mbpMaterial, MbpMaterial.class, "物料表");
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
       return super.importExcel(request, response, MbpMaterial.class);
   }

}
