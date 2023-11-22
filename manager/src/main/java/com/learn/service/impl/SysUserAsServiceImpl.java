package com.learn.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.learn.dto.system.SearchDto;
import com.learn.entity.system.SysUser;
import com.learn.exception.CustomException;
import com.learn.mapper.SysUserAsMapper;
import com.learn.result.ResultCodeEnum;
import com.learn.service.SysUserAsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SysUserAsServiceImpl implements SysUserAsService {

    @Resource
    SysUserAsMapper sysUserAsMapper;
    @Override
    public PageInfo<SysUser> findPage(Integer pageNum, Integer pageSize, SearchDto searchDto) {
        /*开启分页，填入pageNum和pageSize,count:是否统计数量为 true reasonable:合理化为true pageSizeZero:不能为0 false*/
        PageHelper.startPage(pageNum,pageSize,true,true,false);
        List<SysUser> sysUserList =sysUserAsMapper.findPage(searchDto);
        return new PageInfo<>(sysUserList);
    }

    @Override
    public void addUser(SysUser sysUser) {
        /*增加用户的时候 后端检验不能为空，否则抛异常*/
        /*检验前端传过来的参数*/
        String username = sysUser.getUsername();
        String password = sysUser.getPassword();
        String name = sysUser.getName();
        String phone = sysUser.getPhone();
        String avatar = sysUser.getAvatar();
        String description = sysUser.getDescription();
        if ("".equals(username)||"".equals(password)||"".equals(name)||"".equals(phone)||"".equals(avatar)||"".equals(description)){
            throw new CustomException(ResultCodeEnum.INPUT_ERROR_PARAMAMISTAKE,null);
        }

        // 后端系统自动设置的部分
        //每增加一个账户系统自己生成id,用hutool包的雪花算法生成（保证唯一性）
        String idStr = IdUtil.getSnowflake(1, 1).nextIdStr();
        long id = Long.parseLong(idStr);
        sysUser.setId(id);
        //自动创建，创建时间，和更新时间
        sysUser.setCreateTime(new Date());
        sysUser.setUpdateTime(new Date());

        //默认状态，和是否逻辑删除都自动设置，在数据库中已经设置了默认值
        /*sysUser.setIsDeleted(0);*/
        /*sysUser.setStatus(0);*/

        //对前端传过来的数据需要进行进行操作数据（比如密码----进行MD5加密）
        sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));

        sysUserAsMapper.addUser(sysUser);
    }

    @Override
    public void delById(Long id) {
        sysUserAsMapper.delById(id);
    }

    @Override
    public SysUser selectById(Long id) {
        return sysUserAsMapper.selectById(id);

    }

    @Override
    public void updateUser(SysUser sysUser) {
        sysUserAsMapper.updateUser(sysUser);
    }
}
