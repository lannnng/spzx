package com.learn.controller;


import com.github.pagehelper.PageInfo;
import com.learn.entity.system.SysRole;
import com.learn.result.Result;
import com.learn.service.SysRoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/system/role")
// @CrossOrigin 在拦截器注册类里面统一设置跨域
@Tag(name = "角色管理接口")
public class SysRoleController {
    @Resource
    SysRoleService sysRoleService;

    /*更新对象步骤1.获取到row对象的id,查询到数据库中的信息展示在对话框中*/
    @GetMapping("update/{id}")
    public Result selectById(@PathVariable("id") Long id){
        /*通过前端传给接口的id，返回给前端SysRole对象*/
        SysRole sysRole=sysRoleService.updateById(id);
        return Result.success().data(sysRole);
    }
    /*更新对象步骤2.获取到上面的对象，修改里面的信息*/
    @PostMapping("update/sysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole){
        System.out.println(sysRole);
        sysRoleService.updateSysRole(sysRole);
        return Result.success();
    }


    /*添加角色的接口*/
    @PostMapping("page/add")
    public Result addRole(@RequestBody SysRole sysRole){
        sysRoleService.addRole(sysRole);
        return Result.success();
    }

    /*根据前端的 row 对象的id（数据库里面的id渲染展示在前端的行对象的id属性中） 删除一整行（逻辑删除）*/
    @GetMapping("page/{id}")
    public Result delRoleById(@PathVariable("id")Long id) {
        sysRoleService.delRoleById(id);
        return Result.success();
    }

    /*分页查询所有角色信息（页面渲染的时候就加载），当用keyword搜索的时候，也调用这个方法*/
    @GetMapping("page/{pageNum}/{pageSize}")
    public Result page(
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("pageSize") Integer pageSize,
            String keyword
    ) {
        //返回给前端的是 pageInfo(分页数据对象，除了包含查询数据库的对象，还包含分页数据对象)
        PageInfo<SysRole> pageInfo = sysRoleService.findPage(pageNum, pageSize, keyword);
        return Result.success().data(pageInfo);
    }




}
