package com.learn.service;

import com.github.pagehelper.PageInfo;
import com.learn.dto.system.SearchDto;
import com.learn.entity.system.SysUser;

public interface SysUserAsService {

    PageInfo<SysUser> findPage(Integer pageNum, Integer pageSize, SearchDto searchDto);

    void addUser(SysUser sysUser);

    void delById(Long id);

    SysUser selectById(Long id);

    void updateUser(SysUser sysUser);
}
