package com.onlinestore.service.impl;

import com.onlinestore.dao.OrderDao;
import com.onlinestore.dao.impl.OrderDaoImpl;
import com.onlinestore.pojo.Order;
import com.onlinestore.service.OrderService;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 * @author 涂鏊飞tu_aofei@163.com
 * @description: TODO 类描述
 * @create 2021-09-02 19:02
 */
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;

    public OrderServiceImpl() {
        orderDao = new OrderDaoImpl();
    }

    @Override
    public Integer updateOrder(String user) throws SQLException, ParseException {
        return orderDao.updateOrder(user);
    }

    @Override
    public List<Order> queryUserOrder(String user) throws SQLException {
        return orderDao.queryUserOrder(user);
    }
}
