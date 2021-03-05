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
import org.jeecg.modules.mqtt.entity.MbpWorkprocess;
import org.jeecg.modules.mqtt.service.IMbpWorkprocessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
* @Description: 工序
* @Author: jeecg-boot
* @Date:   2020-09-11
* @Version: V1.0
*/
@Api(tags="工序")
@RestController
@RequestMapping("/system/mbpWorkprocess")
@Slf4j
public class MbpWorkprocessController extends JeecgController<MbpWorkprocess, IMbpWorkprocessService> {
   @Autowired
   private IMbpWorkprocessService mbpWorkprocessService;

   /**
    * 分页列表查询
    *
    * @param mbpWorkprocess
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   @AutoLog(value = "工序-分页列表查询")
   @ApiOperation(value="工序-分页列表查询", notes="工序-分页列表查询")
   @GetMapping(value = "/list")
   public Result<?> queryPageList(MbpWorkprocess mbpWorkprocess,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<MbpWorkprocess> queryWrapper = QueryGenerator.initQueryWrapper(mbpWorkprocess, req.getParameterMap());
       Page<MbpWorkprocess> page = new Page<MbpWorkprocess>(pageNo, pageSize);
       IPage<MbpWorkprocess> pageList = mbpWorkprocessService.page(page, queryWrapper);
       return Result.ok(pageList);
   }

   /**
    *   添加
    *
    * @param mbpWorkprocess
    * @return
    */
   @AutoLog(value = "工序-添加")
   @ApiOperation(value="工序-添加", notes="工序-添加")
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody MbpWorkprocess mbpWorkprocess) {
       mbpWorkprocessService.save(mbpWorkprocess);
       return Result.ok("添加成功！");
   }

   /**
    *  编辑
    *
    * @param mbpWorkprocess
    * @return
    */
   @AutoLog(value = "工序-编辑")
   @ApiOperation(value="工序-编辑", notes="工序-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody MbpWorkprocess mbpWorkprocess) {
       mbpWorkprocessService.updateById(mbpWorkprocess);
       return Result.ok("编辑成功!");
   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @AutoLog(value = "工序-通过id删除")
   @ApiOperation(value="工序-通过id删除", notes="工序-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       mbpWorkprocessService.removeById(id);
       return Result.ok("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @AutoLog(value = "工序-批量删除")
   @ApiOperation(value="工序-批量删除", notes="工序-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.mbpWorkprocessService.removeByIds(Arrays.asList(ids.split(",")));
       return Result.ok("批量删除成功!");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @AutoLog(value = "工序-通过id查询")
   @ApiOperation(value="工序-通过id查询", notes="工序-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       MbpWorkprocess mbpWorkprocess = mbpWorkprocessService.getById(id);
       if(mbpWorkprocess==null) {
           return Result.error("未找到对应数据");
       }
       return Result.ok(mbpWorkprocess);
   }

   /**
   * 导出excel
   *
   * @param request
   * @param mbpWorkprocess
   */
   @RequestMapping(value = "/exportXls")
   public ModelAndView exportXls(HttpServletRequest request, MbpWorkprocess mbpWorkprocess) {
       return super.exportXls(request, mbpWorkprocess, MbpWorkprocess.class, "工序");
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
       return super.importExcel(request, response, MbpWorkprocess.class);
   }

}
