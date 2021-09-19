package com.onlinestore.service.impl;

import com.onlinestore.dao.ShopCarDao;
import com.onlinestore.dao.impl.ShopCarDaoImpl;
import com.onlinestore.pojo.ShopCar;
import com.onlinestore.service.ShopCarService;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 涂鏊飞tu_aofei@163.com
 * @description: TODO 类描述
 * @create 2021-09-02 15:56
 */
public class ShopCarServiceImpl implements ShopCarService {
    private ShopCarDao shopCarDao;

    public ShopCarServiceImpl() {
        shopCarDao = new ShopCarDaoImpl();
    }

    @Override
    public Integer updateShopCar(Integer no, String account, Integer count) throws SQLException {
        return shopCarDao.updateShopCar(no, account, count);
    }

    @Override
    public List<ShopCar> queryUserShopCar(String account) throws SQLException {
        return shopCarDao.queryUserShopCar(account);
    }

    @Override
    public Integer deleteUserShopCar(String account) throws SQLException {
        return shopCarDao.deleteUserShopCar(account);
    }
}
