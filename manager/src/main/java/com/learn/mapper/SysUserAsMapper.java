package com.learn.mapper;


import com.learn.dto.system.SearchDto;
import com.learn.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserAsMapper {
    List<SysUser> findPage(@Param("searchDto") SearchDto searchDto);

    void addUser(@Param("sysUser") SysUser sysUser);

    void delById(Long id);

    SysUser selectById(Long id);

    void updateUser(@Param("sysUser") SysUser sysUser);
}
