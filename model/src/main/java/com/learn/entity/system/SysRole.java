package com.learn.entity.system;

import com.learn.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysRole extends BaseEntity {
    @Schema(title = "角色名称")
    private String roleName;
    @Schema(title = "角色代码")
    private String roleCode;
    @Schema(description = "角色描述")
    private String description;

}
