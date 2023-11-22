package com.learn.service;

import com.github.pagehelper.PageInfo;
import com.learn.entity.system.SysRole;

public interface SysRoleService {
    PageInfo<SysRole> findPage(Integer pageNum, Integer pageSize, String keyword);

    void delRoleById(Long id);

    void addRole(SysRole sysRole);

    SysRole updateById(Long id);

    void updateSysRole(SysRole sysRole);
}
