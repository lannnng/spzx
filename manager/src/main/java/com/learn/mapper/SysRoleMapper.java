package com.learn.mapper;

import com.learn.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    List<SysRole> findPage(String keyword);

    void delRoleById(Long id);

    void addRole(SysRole sysRole);

    SysRole updateById(Long id);

    void updateSysRole(SysRole sysRole);
}
