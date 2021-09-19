package com.onlinestore.service;

import com.onlinestore.pojo.UserInformation;

import java.sql.SQLException;
import java.util.List;

public interface UserInformationService {
    public void register(UserInformation userInformation) throws SQLException;
    public Integer login(String user,String password) throws SQLException;
    public String[] getMenu(String account) throws SQLException;
}
