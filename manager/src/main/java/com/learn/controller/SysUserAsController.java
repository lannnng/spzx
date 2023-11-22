package com.learn.controller;

import com.github.pagehelper.PageInfo;
import com.learn.dto.system.SearchDto;
import com.learn.entity.system.SysRole;
import com.learn.entity.system.SysUser;
import com.learn.result.Result;
import com.learn.service.SysUserAsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@Tag(name = "账户管理模块")
@RequestMapping("/admin/system/user")
@RestController
public class SysUserAsController {

    @Resource
    SysUserAsService sysUserAsService;

    @Operation(summary = "条件查询及分页查询")
    @GetMapping("/page/{pageNum}/{pageSize}")
    public Result page(@PathVariable Integer pageNum,
                       @PathVariable Integer pageSize,
                       SearchDto searchDto/*搜索输入的条件，包装成DTO传过来*/){
        PageInfo<SysUser> sysUserPageInfo=sysUserAsService.findPage(pageNum,pageSize,searchDto);
        return Result.success().data(sysUserPageInfo);
    }

    @Operation(summary = "新增用户")
    @PostMapping("/add")
    public Result add(@RequestBody SysUser sysUser){
        //@RequestBody加载前端获取到输入的属性放在sysUser里面
        sysUserAsService.addUser(sysUser);
        return Result.success();
    }

    @Operation(summary = "删除用户(逻辑删除)")
    @GetMapping("/del/{id}")
    public Result delById(@PathVariable Long id){
        sysUserAsService.delById(id);
        return Result.success();
    }

    @Operation(summary = "查询用户回显配合更新用户的接口")
    @GetMapping("/select/{id}")
    public Result selectById(@PathVariable Long id){
        SysUser sysUser=sysUserAsService.selectById(id);
        return Result.success().data(sysUser);
    }

    @Operation(summary = "更新用户")
    @PostMapping("/update")
    public Result update(@RequestBody SysUser sysUser){
        sysUserAsService.updateUser(sysUser);
        return  Result.success();
    }

}
