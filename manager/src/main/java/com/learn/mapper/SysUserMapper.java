package com.learn.mapper;

import com.learn.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {
   SysUser findSysUserByUserName(String userName);
}
