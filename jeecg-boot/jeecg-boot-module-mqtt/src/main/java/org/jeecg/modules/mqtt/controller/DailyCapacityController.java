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
import org.jeecg.modules.mqtt.entity.DailyCapacity;
import org.jeecg.modules.mqtt.service.IDailyCapacityService;
import org.jeecg.modules.mqtt.vo.DailyCapacityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
* @Description: 每日产能
* @Author: jeecg-boot
* @Date:   2020-10-10
* @Version: V1.0
*/
@Slf4j
@Api(tags="每日产能")
@RestController
@RequestMapping("/system/dailyCapacity")
public class DailyCapacityController extends JeecgController<DailyCapacity, IDailyCapacityService> {
   @Autowired
   private IDailyCapacityService dailyCapacityService;

    /**
     * 生产数量 -- 根据设备查询今日生产数量
     * @return
     */
    @ApiOperation(value = "每日产能-根据设备查询今日生产数量", notes = "每日产能-根据设备查询今日生产数量")
    @GetMapping(value = "/sumByEquipment")
    public Result<?> sumByEquipment(DailyCapacityVO dailyCapacityVO) {
        Result<DailyCapacityVO> result = new Result<DailyCapacityVO>();
        DailyCapacityVO vo = new DailyCapacityVO();
        QueryWrapper<DailyCapacity> queryWrapper = new QueryWrapper<>();
        String lastSql = "where DateDiff(dd,create_time,getdate())=0 and equipmentsn = '" + dailyCapacityVO.getEquipmentsn() + "'";
        queryWrapper.last(lastSql);
        List<DailyCapacity> list = dailyCapacityService.list(queryWrapper);
        Integer sum = list.stream().mapToInt(DailyCapacity::getCount).sum();
        vo.setDSum(sum);
        queryWrapper = new QueryWrapper<>();
        lastSql = "where DateDiff(month,create_time,getdate())=0 and equipmentsn = '" + dailyCapacityVO.getEquipmentsn() + "'";
        queryWrapper.last(lastSql);
        list = dailyCapacityService.list(queryWrapper);
        sum = list.stream().mapToInt(DailyCapacity::getCount).sum();
        vo.setMSum(sum);
        result.setResult(vo);
        return result;
    }

    /**
     * 产量对比 --根据设备，条件查询
     * @return
     */
    @ApiOperation(value = "每日产能-产量对比", notes = "每日产能-产量对比")
    @GetMapping(value = "/groupByEquipment")
    public Result<?> groupByEquipment(DailyCapacityVO dailyCapacityVO) {
        Result<List<DailyCapacityVO>> result = new Result<List<DailyCapacityVO>>();
        List<DailyCapacityVO> list = dailyCapacityService.groupByEquipment(dailyCapacityVO);
        result.setResult(list);
        return result;
    }

    /**
     * 按月统计
     * @return
     */
    @ApiOperation(value = "每日产能-按月统计", notes = "每日产能-按月统计")
    @GetMapping(value = "/querySumMonthCapacity")
    public Result<?> querySumMonthCapacity() throws ParseException {
        Result<List<DailyCapacityVO>> result = new Result<List<DailyCapacityVO>>();

        /*得到当前年份*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        String year = simpleDateFormat.format(new Date());
        List<DailyCapacityVO> list = new ArrayList<DailyCapacityVO>();
        for (Integer i = 1 ; i < 13 ; i++) {
            String time = "";
            String month = "";
            if ( i < 10) {
                time = (year+"-0"+i);

                month = ("0"+i);
            }else {
                time = (year+"-"+i);
                month = i.toString();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Date parse = sdf.parse(time);
            Integer sum = dailyCapacityService.sumMonthCapacity(parse);
            DailyCapacityVO vo = new DailyCapacityVO();
            vo.setTime(month);
            vo.setCount(sum);
            list.add(vo);
        }
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 按天统计
     * @return
     */
    @ApiOperation(value = "每日产能-按天统计", notes = "每日产能-按天统计")
    @GetMapping(value = "/querySumDayCapacity")
    public Result<?> querySumDayCapacity() throws ParseException {
        Result<List<DailyCapacityVO>> result = new Result<List<DailyCapacityVO>>();
       /* 得到当前日期*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //String day = simpleDateFormat.format(new Date());
        List<DailyCapacityVO> list = new ArrayList<DailyCapacityVO>();
        Integer nowCount = 0;
        for (Integer i = 0 ; i < 10 ; i++) {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            now.set(Calendar.DATE,now.get(Calendar.DATE) - i);
            Date time = now.getTime();
            Integer sum = dailyCapacityService.sumDayCapacity(time);
            DailyCapacityVO vo = new DailyCapacityVO();
            vo.setTime(simpleDateFormat.format(time));
            vo.setCount(sum);
            if (i == 0) {
               nowCount = sum;
            }
            vo.setNowCount(nowCount);
            list.add(vo);
        }
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }


    /**
     * 按天统计设备上线状况
     * @return
     */
    @ApiOperation(value = "每日产能-按天统计设备上线状况", notes = "每日产能-按天统计设备上线状况")
    @GetMapping(value = "/queryCountDayEquipmentOnline")
    public Result<?> queryCountDayEquipmentOnline() throws ParseException {
        Result<List<DailyCapacityVO>> result = new Result<List<DailyCapacityVO>>();
        /* 得到当前日期*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        List<DailyCapacityVO> list = new ArrayList<DailyCapacityVO>();
        for (Integer i = 0 ; i < 7 ; i++) {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            now.set(Calendar.DATE,now.get(Calendar.DATE) - i);
            Date time = now.getTime();
            Integer count = dailyCapacityService.countDayEquipmentOnline(time);
            DailyCapacityVO vo = new DailyCapacityVO();
            vo.setTime(sdf.format(time));
            vo.setCount(count);
            list.add(vo);
        }
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }


   /**
    * 分页列表查询
    *
    * @param dailyCapacity
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   //@AutoLog(value = "每日产能-分页列表查询")
   @ApiOperation(value="每日产能-分页列表查询", notes="每日产能-分页列表查询")
   @GetMapping(value = "/list")
   public Result<?> queryPageList(DailyCapacity dailyCapacity,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<DailyCapacity> queryWrapper = QueryGenerator.initQueryWrapper(dailyCapacity, req.getParameterMap());
       Page<DailyCapacity> page = new Page<DailyCapacity>(pageNo, pageSize);
       IPage<DailyCapacity> pageList = dailyCapacityService.page(page, queryWrapper);
       return Result.ok(pageList);
   }

   /**
    * 添加
    *
    * @param dailyCapacity
    * @return
    */
   @AutoLog(value = "每日产能-添加")
   @ApiOperation(value="每日产能-添加", notes="每日产能-添加")
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody DailyCapacity dailyCapacity) {
       dailyCapacityService.save(dailyCapacity);
       return Result.ok("添加成功！");
   }

   /**
    * 编辑
    *
    * @param dailyCapacity
    * @return
    */
   @AutoLog(value = "每日产能-编辑")
   @ApiOperation(value="每日产能-编辑", notes="每日产能-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody DailyCapacity dailyCapacity) {
       dailyCapacityService.updateById(dailyCapacity);
       return Result.ok("编辑成功!");
   }

   /**
    * 通过id删除
    *
    * @param id
    * @return
    */
   @AutoLog(value = "每日产能-通过id删除")
   @ApiOperation(value="每日产能-通过id删除", notes="每日产能-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       dailyCapacityService.removeById(id);
       return Result.ok("删除成功!");
   }

   /**
    * 批量删除
    *
    * @param ids
    * @return
    */
   @AutoLog(value = "每日产能-批量删除")
   @ApiOperation(value="每日产能-批量删除", notes="每日产能-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.dailyCapacityService.removeByIds(Arrays.asList(ids.split(",")));
       return Result.ok("批量删除成功！");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @AutoLog(value = "每日产能-通过id查询")
   @ApiOperation(value="每日产能-通过id查询", notes="每日产能-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       DailyCapacity dailyCapacity = dailyCapacityService.getById(id);
       return Result.ok(dailyCapacity);
   }

 /**
  * 导出excel
  *
  * @param request
  * @param dailyCapacity
  */
 @RequestMapping(value = "/exportXls")
 public ModelAndView exportXls(HttpServletRequest request, DailyCapacity dailyCapacity) {
     return super.exportXls(request, dailyCapacity, DailyCapacity.class, "每日产能");
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
     return super.importExcel(request, response, DailyCapacity.class);
 }

}
