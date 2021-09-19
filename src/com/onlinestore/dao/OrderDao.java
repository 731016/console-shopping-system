package com.onlinestore.dao;

import com.onlinestore.pojo.Order;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface OrderDao {
    // 提交订单
    public Integer  updateOrder(String user) throws SQLException, ParseException;
    // 查看用户订单
    public List<Order> queryUserOrder(String user) throws SQLException;
}
