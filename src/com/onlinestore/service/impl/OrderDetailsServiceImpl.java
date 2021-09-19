package com.onlinestore.service.impl;

import com.onlinestore.dao.OrderDetailsDao;
import com.onlinestore.dao.impl.OrderDetailsDaoImpl;
import com.onlinestore.pojo.OrderDetails;
import com.onlinestore.service.OrderDetailsService;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 涂鏊飞tu_aofei@163.com
 * @description: TODO 类描述
 * @create 2021-09-03 11:44
 */
public class OrderDetailsServiceImpl implements OrderDetailsService {
    private OrderDetailsDao orderDetails;

    public OrderDetailsServiceImpl() {
        orderDetails = new OrderDetailsDaoImpl();
    }

    @Override
    public Integer addOrderDetails(String account) throws SQLException {
        return orderDetails.addOrderDetails(account);
    }

    @Override
    public List<OrderDetails> queryAllOrderDetails(String account) throws SQLException {
        return orderDetails.queryAllOrderDetails(account);
    }
}
