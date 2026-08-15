package com.atguigu.ssyx.sys.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.sys.Region;
import com.atguigu.ssyx.sys.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 地区表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2026-05-11
 */
@RestController
@RequestMapping("/admin/sys/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    //根据区域关键字查询列表信息
    @ApiOperation("根据区域关键字查询列表信息")
    @GetMapping("findRegionByKeyWord/{keyword}")
    public Result findRegionByKeyWord(@PathVariable("keyword") String keyword){

        List<Region> list = regionService.getRegionByKeyword(keyword);
        return Result.ok(list);

    }


}

