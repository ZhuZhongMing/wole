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
import org.jeecg.modules.mqtt.entity.CNCModel;
import org.jeecg.modules.mqtt.entity.DailyCapacity;
import org.jeecg.modules.mqtt.service.ICNCModelService;
import org.jeecg.modules.mqtt.vo.DailyCapacityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 杭州三米明德科技有限公司
 * CNC数据模型
 * @author zzm
 * date 2020-07-24
 */
@Api(tags="CNC数据模型")
@RestController
@RequestMapping("/mqtt/cncModel")
@Slf4j
public class CNCModelController extends JeecgController<CNCModel, ICNCModelService> {

    @Autowired
    private ICNCModelService service;

    /**
     *  采集频率 -- 根据设备查询
     * @return
     */
    @ApiOperation(value = "采集频率 -- 根据设备查询", notes = "采集频率 -- 根据设备查询")
    @GetMapping(value = "/countCncModel")
    public Result<?> countCncModel(CNCModel cncModel) {
        Result<List<CNCModel>> result = new Result<List<CNCModel>>();
        List<CNCModel> list = service.countCncModel(cncModel);
        result.setResult(list);
        return result;
    }

    /**
     *  警报次数 -- 根据设备统计警报次数
     * @return
     */
    @ApiOperation(value = "CNC数据模型-根据设备统计警报次数", notes = "CNC数据模型-根据设备统计警报次数")
    @GetMapping(value = "/coutAlarm")
    public Result<?> coutAlarm(CNCModel cncModel) {
        Result<DailyCapacityVO> result = new Result<DailyCapacityVO>();
        DailyCapacityVO vo = new DailyCapacityVO();
        QueryWrapper<CNCModel> queryWrapper = new QueryWrapper<>();
        String lastSql = "where DateDiff(dd,create_time,getdate())=0 and alarminfo != '' and cncsn = '" + cncModel.getCncsn() + "'";
        queryWrapper.last(lastSql);
        int count = service.count(queryWrapper);
        vo.setDSum(count);
        queryWrapper = new QueryWrapper<>();
        lastSql = "where alarminfo != '' and cncsn = '" + cncModel.getCncsn() + "'";
        queryWrapper.last(lastSql);
        count = service.count(queryWrapper);
        vo.setMSum(count);
        result.setResult(vo);
        return result;
    }

    /**
     * 查询最新的指定设备名称的 cnc 数据
     * @return
     */
    @ApiOperation(value="查询最新的指定设备名称的 cnc 数据", notes="查询最新的指定设备名称的 cnc 数据")
    @GetMapping(value = "/queryByEquipmentId")
    public Result<?> queryByEquipmentId(CNCModel CNCModel) {
        QueryWrapper<CNCModel> queryWrapper =  new QueryWrapper<>();
        String lastSql = "where id in (select max(id) from cnc_model where cncsn = '" + CNCModel.getCncsn() +"')";
        queryWrapper.last(lastSql);
        CNCModel model = null;
        try {
            model = service.list(queryWrapper).get(0);
        } catch (IndexOutOfBoundsException e) {
            log.info("下标越界");
        }
        return Result.ok(model);
    }

    /**
     * 查询指定设备报警数据
     * @return
     */
    @ApiOperation(value="查询指定设备报警数据", notes="查询指定设备报警数据")
    @GetMapping(value = "/queryAlarmByEId")
    public Result<?> queryAlarmByEId(CNCModel CNCModel) {
        List<org.jeecg.modules.mqtt.entity.CNCModel> list = service.queryAlarmByEId(CNCModel);
        Collections.reverse(list);
        return Result.ok(list);
    }

    /**
     * 分页列表查询
     *
     * @param CNCModel
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "cnc数据-分页列表查询")
    @ApiOperation(value="cnc数据-分页列表查询", notes="cnc数据-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(CNCModel CNCModel,
                                   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<CNCModel> queryWrapper = QueryGenerator.initQueryWrapper(CNCModel, req.getParameterMap());
        Page<CNCModel> page = new Page<CNCModel>(pageNo, pageSize);
        IPage<CNCModel> pageList = service.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     *   添加
     *
     * @param cncModel
     * @return
     */
    @AutoLog(value = "cnc数据-添加")
    @ApiOperation(value="cnc数据-添加", notes="cnc数据-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody CNCModel cncModel) {
        service.save(cncModel);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     *
     * @param cncModel
     * @return
     */
    @AutoLog(value = "cnc数据-编辑")
    @ApiOperation(value="cnc数据-编辑", notes="cnc数据-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody CNCModel cncModel) {
        service.updateById(cncModel);
        return Result.OK("编辑成功!");
    }

    /**
     *   通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "cnc数据-通过id删除")
    @ApiOperation(value="cnc数据-通过id删除", notes="cnc数据-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        service.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     *  批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "cnc数据-批量删除")
    @ApiOperation(value="cnc数据-批量删除", notes="cnc数据-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.service.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "cnc数据-通过id查询")
    @ApiOperation(value="cnc数据-通过id查询", notes="cnc数据-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
        CNCModel cncModel = service.getById(id);
        if(cncModel==null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(cncModel);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param cncModel
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CNCModel cncModel) {
        return super.exportXls(request, cncModel, CNCModel.class, "cnc数据");
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
        return super.importExcel(request, response, CNCModel.class);
    }
    
    


}
