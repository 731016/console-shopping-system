package com.onlinestore.service.impl;

import com.onlinestore.dao.UserInformationDao;
import com.onlinestore.dao.impl.UserInformationDaoImpl;
import com.onlinestore.pojo.UserInformation;
import com.onlinestore.service.UserInformationService;

import java.sql.SQLException;

/**
 * @author 涂鏊飞tu_aofei@163.com
 * @description: 用户逻辑层 用户登录和注册 类描述
 * @create 2021-09-01 17:27
 */
public class UserInformationServiceImpl implements UserInformationService {
    private UserInformationDao userInformationDao;

    public UserInformationServiceImpl() {
        userInformationDao = new UserInformationDaoImpl();
    }

    @Override
    public void register(UserInformation ui) throws SQLException {
        userInformationDao.register(ui);
    }

    @Override
    public Integer login(String user, String password) throws SQLException {
        Integer loginFlag = userInformationDao.login(user, password);
        return loginFlag;
    }

    @Override
    public String[] getMenu(String account) throws SQLException {
        return userInformationDao.getMenu(account);
    }
}
