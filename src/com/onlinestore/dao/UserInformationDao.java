package com.onlinestore.dao;

import com.onlinestore.pojo.UserInformation;
import com.onlinestore.pojo.UserRole;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;
import java.util.List;

public interface UserInformationDao {
    // 注册
    public void register(UserInformation userInformation) throws SQLException;
    // 登录
    public Integer login(String user,String password) throws SQLException;
    // 获取用户状态
    public Integer getState(String user) throws SQLException;
    // 获取用户操作菜单
    public String[] getMenu(String account) throws SQLException;
}
