package com.onlinestore.pojo;

import lombok.*;

/**
 * @author 涂鏊飞tu_aofei@163.com
 * @description: 用户角色表 类描述
 * @create 2021-09-01 17:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
    private Integer roleid;
    private String rolename;
    private String menulist;
}
