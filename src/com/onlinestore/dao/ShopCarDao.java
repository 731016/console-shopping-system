package com.onlinestore.dao;

import com.onlinestore.pojo.ShopCar;

import java.sql.SQLException;
import java.util.List;

public interface ShopCarDao {
    // 加入购物车
    public Integer updateShopCar(Integer no, String account, Integer count) throws SQLException;

    // 展示购物车
    public List<ShopCar> queryUserShopCar(String account) throws SQLException;

    // 提交订单后，删除指定用户的购物车记录
    public Integer deleteUserShopCar(String account) throws SQLException;
}
