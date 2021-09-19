package com.onlinestore.service;

import com.onlinestore.pojo.OrderDetails;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsService {
    public Integer addOrderDetails(String account) throws SQLException;
    public List<OrderDetails> queryAllOrderDetails(String account) throws SQLException;
}
