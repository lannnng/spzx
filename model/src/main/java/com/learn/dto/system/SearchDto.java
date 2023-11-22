package com.learn.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

import java.util.Date;

@Schema(name = "查询用户的条件")
@Data
public class SearchDto {
    @Schema(description = "查询的关键字")
    private String keyword;
    @Schema(description = "创建起始时间")
    private String creatTimeStart;
    @Schema(description = "创建的结束时间")
    private String creatTimeEnd;


}
