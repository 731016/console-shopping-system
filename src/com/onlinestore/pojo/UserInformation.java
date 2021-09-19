package com.onlinestore.pojo;

import lombok.*;

/**
 * @author 涂鏊飞tu_aofei@163.com
 * @description: 数据库映射表-用户表 类描述
 * @create 2021-09-01 16:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserInformation {
    private String user;
    private String password;
    private String uname;
    private Integer roleid;
    private Integer status;
}
