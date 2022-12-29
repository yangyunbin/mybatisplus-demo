package com.example.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.example.enums.SexEnum;
import lombok.Data;

@Data
//@TableName(value = "t_user")
public class User {

    //将属性对应的字段指定为主键
    //@TableId的value属性指定主键的字段
    //type属性用来定义主键策略
    @TableId(value = "uid",type = IdType.AUTO)
    private Long id;

    //实体类属性上使用@TableField("user_name")设置属性所对应的字段名
    @TableField(value = "user_name")
    private String name;

    private Integer age;

    private String email;

    @TableLogic
    private Integer isDelete;

    private SexEnum sex;
}
