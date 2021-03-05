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
import org.jeecg.modules.mqtt.entity.MbpCustomer;
import org.jeecg.modules.mqtt.service.IMbpCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description: 客户表
 * @Author: jeecg-boot
 * @Date: 2020-08-17
 * @Version: V1.0
 */
@Api(tags = "客户表")
@RestController
@RequestMapping("/system/mbpCustomer")
@Slf4j
public class MbpCustomerController extends JeecgController<MbpCustomer, IMbpCustomerService> {
    @Autowired
    private IMbpCustomerService mbpCustomerService;

    /**
     * 全查询
     *
     * @param mbpCustomer
     * @param req
     * @return
     */
    //@AutoLog(value = "客户表-全查询")
    @ApiOperation(value = "客户表-全查询", notes = "客户表-全查询")
    @GetMapping(value = "/allList")
    public Result<?> allList(MbpCustomer mbpCustomer,
                             HttpServletRequest req) {
        QueryWrapper<MbpCustomer> queryWrapper = QueryGenerator.initQueryWrapper(mbpCustomer, req.getParameterMap());
        List<MbpCustomer> pageList = mbpCustomerService.list(queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 分页列表查询
     *
     * @param mbpCustomer
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "客户表-分页列表查询")
    @ApiOperation(value = "客户表-分页列表查询", notes = "客户表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(MbpCustomer mbpCustomer,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<MbpCustomer> queryWrapper = QueryGenerator.initQueryWrapper(mbpCustomer, req.getParameterMap());
        Page<MbpCustomer> page = new Page<MbpCustomer>(pageNo, pageSize);
        IPage<MbpCustomer> pageList = mbpCustomerService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param mbpCustomer
     * @return
     */
    @AutoLog(value = "客户表-添加")
    @ApiOperation(value = "客户表-添加", notes = "客户表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody MbpCustomer mbpCustomer) {
        // 添加操作之前，获取当前时间记录 - 到毫秒
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String beforeSave = sdf.format(new Date());
        log.info("before : " + beforeSave);
        mbpCustomerService.save(mbpCustomer);
        //添加成功将id查出返回至前端
        QueryWrapper<MbpCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("create_time", beforeSave);
        List<MbpCustomer> list = mbpCustomerService.list(queryWrapper);
        MbpCustomer customer = new MbpCustomer();
        customer.setId(list.get(0).getId());
        Result<MbpCustomer> result = new Result<>();
        result.setResult(customer);
        result.setSuccess(true);
        result.setMessage("添加成功！");
        return result;
    }

    /**
     * 编辑
     *
     * @param mbpCustomer
     * @return
     */
    @AutoLog(value = "客户表-编辑")
    @ApiOperation(value = "客户表-编辑", notes = "客户表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody MbpCustomer mbpCustomer) {
        mbpCustomerService.updateById(mbpCustomer);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "客户表-通过id删除")
    @ApiOperation(value = "客户表-通过id删除", notes = "客户表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        mbpCustomerService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "客户表-批量删除")
    @ApiOperation(value = "客户表-批量删除", notes = "客户表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.mbpCustomerService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "客户表-通过id查询")
    @ApiOperation(value = "客户表-通过id查询", notes = "客户表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        MbpCustomer mbpCustomer = mbpCustomerService.getById(id);
        if (mbpCustomer == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(mbpCustomer);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param mbpCustomer
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MbpCustomer mbpCustomer) {
        return super.exportXls(request, mbpCustomer, MbpCustomer.class, "客户表");
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
        return super.importExcel(request, response, MbpCustomer.class);
    }

}
