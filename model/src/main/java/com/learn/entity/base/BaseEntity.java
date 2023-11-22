package com.learn.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/*所有表都有的字段*/
@Data
public class BaseEntity {
    /*一般用Long类型存储id,保证id唯一,以后会雪花算法，保存id,长度19位*/
   @Schema(title = "角色id,一般用雪花算法生成")
    private Long id;
    @Schema(title = "创建时间，service层自动生成")
    private Date createTime;
    @Schema(title = "更新时间，service层自动生成")
    private Date updateTime;
    @Schema(title = "是否被删除，0表示未删除，1表示逻辑删除")
    private Integer isDeleted;
}
