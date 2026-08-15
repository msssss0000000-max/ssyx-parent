package com.atguigu.ssyx.acl.controllor;


import com.atguigu.ssyx.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags="登录接口")
@RequestMapping("/admin/acl/index")
@RestController
public class IndexController {
    ///admin/acl/index/login
    //1.login登录
    @ApiOperation("登录")
    @PostMapping("login")
    public Result login(){
        Map<String, String> map = new HashMap<>();
        map.put("token", "token-admin");
        return Result.ok(map);
    }

    //2.getInfo获取信息
    ///admin/acl/index/info
    @ApiOperation("获取信息")
    @GetMapping("info")
    public Result info(){
        Map<String, String> map = new HashMap<>();
        map.put("name", "admin");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(map);
    }

    //3.logout退出
    ///admin/acl/index/logout
    @ApiOperation("退出")
    @PostMapping("logout")
    public Result logout(){
        return Result.ok(null);
    }
}
