package com.learn.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.learn.entity.system.SysRole;
import com.learn.exception.CustomException;
import com.learn.mapper.SysRoleMapper;
import com.learn.result.ResultCodeEnum;
import com.learn.service.SysRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    SysRoleMapper sysRoleMapper;

    @Override
    public PageInfo<SysRole> findPage(Integer pageNum, Integer pageSize, String keyword) {
        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> sysRoleLists = sysRoleMapper.findPage(keyword);
        return new PageInfo<SysRole>(sysRoleLists);
    }

    @Override
    public void delRoleById(Long id) {
        sysRoleMapper.delRoleById(id);
    }

    @Override
    public void addRole(SysRole sysRole) {
        //前端应该要先控制一下，如果是空的就不能输入
        // 后端在这里应该要校验一下，如果为空，就抛异常
        String roleName = sysRole.getRoleName();
        String roleCode = sysRole.getRoleCode();
        String description = sysRole.getDescription();
        // "".equals(roleName)  "" 必须写在外面，防止空指针异常
        if ("".equals(roleName)||"".equals(roleCode)||"".equals(description)) {
            throw new CustomException(ResultCodeEnum.INPUT_ERROR_PARAMAMISTAKE, null);
        }
        //增加角色的时候有些默认的字段自动添加默认值
        //时间单位是mysql数据库内部储存的时间，时间单位要注意
        sysRole.setCreateTime(new Date());
        sysRole.setUpdateTime(new Date());
// 数据库已经设置了默认为0，这里就不用写了，sql语句也不用写
//        sysRole.setIsDeleted(0);
        sysRoleMapper.addRole(sysRole);
    }

    @Override
    public SysRole updateById(Long id) {
        SysRole sysRole=sysRoleMapper.updateById(id);
        return sysRole;
    }

    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRole.setUpdateTime(new Date());
        sysRoleMapper.updateSysRole(sysRole);
    }
}
