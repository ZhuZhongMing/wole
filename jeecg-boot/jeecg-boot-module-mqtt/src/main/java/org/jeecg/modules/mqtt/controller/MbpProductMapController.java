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
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.mqtt.entity.MbpProductMap;
import org.jeecg.modules.mqtt.service.IMbpProductMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @Description: 产品表
* @Author: jeecg-boot
* @Date:   2020-08-18
* @Version: V1.0
*/
@Api(tags="产品表")
@RestController
@RequestMapping("/system/mbpProductMap")
@Slf4j
public class MbpProductMapController extends JeecgController<MbpProductMap, IMbpProductMapService> {
   @Autowired
   private IMbpProductMapService mbpProductMapService;

    /**
     * 全查询
     *
     * @param mbpProductMap
     * @param req
     * @return
     */
    //@AutoLog(value = "产品表-分页列表查询")
    @ApiOperation(value="产品表-全查询", notes="产品表-全查询")
    @GetMapping(value = "/allList")
    public Result<?> allList(MbpProductMap mbpProductMap,
                             HttpServletRequest req) {
        QueryWrapper<MbpProductMap> queryWrapper = QueryGenerator.initQueryWrapper(mbpProductMap, req.getParameterMap());
        List<MbpProductMap> pageList = mbpProductMapService.list(queryWrapper);
        return Result.ok(pageList);
    }


    /**
     * 全查询 -- 过滤,是否在bom表中存在
     *
     * @param mbpProductMap
     * @param req
     * @return
     */
    @ApiOperation(value="产品表-全查询 -- 过滤,是否在bom表中存在", notes="产品表-全查询 -- 过滤,是否在bom表中存在")
    @GetMapping(value = "/allListFilter")
    public Result<?> allListFilter(MbpProductMap mbpProductMap,
                                   HttpServletRequest req) {
        QueryWrapper<MbpProductMap> queryWrapper = QueryGenerator.initQueryWrapper(new MbpProductMap(), req.getParameterMap()); //不需要加任何条件
        /*默认查询在bom中不存在的产品信息*/
        String sql = "where id not in (select material_id from mbp_bom where parent_id = '0')";
        /*以deFlag来标识，不为空时查询在bom表中存在的产品信息*/
        if(!oConvertUtils.isEmpty(mbpProductMap.getDelFlag())){
            sql = "where id in (select material_id from mbp_bom where parent_id = '0')";
        }
        queryWrapper.last(sql); // 过滤掉在BOM表以设置过的产品
        List<MbpProductMap> pageList = mbpProductMapService.list(queryWrapper);
        return Result.ok(pageList);
    }

   /**
    * 分页列表查询
    *
    * @param mbpProductMap
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   //@AutoLog(value = "产品表-分页列表查询")
   @ApiOperation(value="产品表-分页列表查询", notes="产品表-分页列表查询")
   @GetMapping(value = "/list")
   public Result<?> queryPageList(MbpProductMap mbpProductMap,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       Result<IPage<MbpProductMap>> result = new Result<>();
       QueryWrapper<MbpProductMap> queryWrapper = QueryGenerator.initQueryWrapper(mbpProductMap, req.getParameterMap());
       Page<MbpProductMap> page = new Page<MbpProductMap>(pageNo, pageSize);
       IPage<MbpProductMap> pageList = mbpProductMapService.page(page, queryWrapper);
       //批量查询产品所需的物料
       //step.1 先拿到全部的 ids
       //step.2 通过 ids，一次性查询产品所需的物料名称
       List<String> ids = pageList.getRecords().stream().map(MbpProductMap::getId).collect(Collectors.toList());
       if(ids!=null && ids.size()>0){
           Map<String,String> names = mbpProductMapService.getMaterialNamesByIds(ids);
           pageList.getRecords().forEach(item->{
               item.setMaterialName(names.get(item.getId()));
           });
       }
       result.setResult(pageList);
       result.setSuccess(true);
       return result;
   }

   /**
    *   添加
    *
    * @param mbpProductMap
    * @return
    */
   @AutoLog(value = "产品表-添加")
   @ApiOperation(value="产品表-添加", notes="产品表-添加")
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody MbpProductMap mbpProductMap) {
       mbpProductMapService.save(mbpProductMap);
       return Result.ok("添加成功！");
   }

   /**
    *  编辑
    *
    * @param mbpProductMap
    * @return
    */
   @AutoLog(value = "产品表-编辑")
   @ApiOperation(value="产品表-编辑", notes="产品表-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody MbpProductMap mbpProductMap) {
       mbpProductMapService.updateById(mbpProductMap);
       return Result.ok("编辑成功!");
   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @AutoLog(value = "产品表-通过id删除")
   @ApiOperation(value="产品表-通过id删除", notes="产品表-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       mbpProductMapService.removeById(id);
       return Result.ok("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @AutoLog(value = "产品表-批量删除")
   @ApiOperation(value="产品表-批量删除", notes="产品表-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.mbpProductMapService.removeByIds(Arrays.asList(ids.split(",")));
       return Result.ok("批量删除成功!");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @AutoLog(value = "产品表-通过id查询")
   @ApiOperation(value="产品表-通过id查询", notes="产品表-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       MbpProductMap mbpProductMap = mbpProductMapService.getById(id);
       if(mbpProductMap==null) {
           return Result.error("未找到对应数据");
       }
       return Result.ok(mbpProductMap);
   }

   /**
   * 导出excel
   *
   * @param request
   * @param mbpProductMap
   */
   @RequestMapping(value = "/exportXls")
   public ModelAndView exportXls(HttpServletRequest request, MbpProductMap mbpProductMap) {
       return super.exportXls(request, mbpProductMap, MbpProductMap.class, "产品表");
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
       return super.importExcel(request, response, MbpProductMap.class);
   }

}
