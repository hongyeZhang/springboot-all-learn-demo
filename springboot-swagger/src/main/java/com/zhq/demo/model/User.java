package com.zhq.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class User {

    @ApiModelProperty(value = "id", required = true, example = "1")
    private long id;

    @ApiModelProperty(value = "姓名", required = true, example = "张三")
    private String name;

    @ApiModelProperty(value = "年龄", example = "10")
    private int age;

    @ApiModelProperty(value = "密码", required = true, hidden = true)
    private String password;
}