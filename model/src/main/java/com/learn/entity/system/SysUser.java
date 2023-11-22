package com.learn.entity.system;

import com.learn.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/*从数据库查询到的数据*/
@Data
public class SysUser extends BaseEntity {
     @Schema(title = "用户名")
     private String username;
     @Schema(title = "密码")
     private String password;
     private String name;
     private String phone;
     private String avatar;
     private String description;
     private Integer status;
}




