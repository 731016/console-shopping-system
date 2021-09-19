package com.onlinestore.service;

import com.onlinestore.pojo.ShopCar;

import java.sql.SQLException;
import java.util.List;

public interface ShopCarService {
    public Integer updateShopCar(Integer no,String account,Integer count) throws SQLException;
    public List<ShopCar> queryUserShopCar(String account) throws SQLException;
    public Integer deleteUserShopCar(String account) throws SQLException;

}
