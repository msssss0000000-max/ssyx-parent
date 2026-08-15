package com.atguigu.ssyx.sys.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.sys.Ware;
import com.atguigu.ssyx.sys.service.WareService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 仓库表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2026-05-11
 */
@RestController
@RequestMapping("/admin/sys/ware")
public class WareController {

    @Autowired
    private WareService wareService;

    //查询所有仓库列表
    @ApiOperation("查询所有仓库")
    @GetMapping("findAllList")
    public Result findAllList(){
        List<Ware> list = wareService.list();
        return Result.ok(list);
    }

}

