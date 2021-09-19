package com.onlinestore.service;

import com.onlinestore.pojo.Order;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface OrderService {
    public Integer updateOrder(String user) throws SQLException, ParseException;
    public List<Order> queryUserOrder(String user) throws SQLException;
}
