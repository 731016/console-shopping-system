package com.onlinestore.dao.impl;

import com.onlinestore.dao.OrderDao;
import com.onlinestore.pojo.Order;
import com.onlinestore.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.naming.spi.ObjectFactoryBuilder;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.SimpleFormatter;

/**
 * @author 涂鏊飞tu_aofei@163.com
 * @description: 订单表 类描述
 * @create 2021-09-02 17:19
 */
public class OrderDaoImpl implements OrderDao {
    private QueryRunner runner;

    public OrderDaoImpl() {
        runner = JDBCUtils.getQueryRunner();
    }

    // 根据用户信息,提交订单
    @Override
    public Integer updateOrder(String user) throws SQLException, ParseException {
        String sumMoneysql = "select sum(`money`) from shopCar where `user`=?";
        ScalarHandler<Double> handler = new ScalarHandler<>();
        Object[] params_sum = {
                user
        };
        Double sumMoney = runner.query(sumMoneysql, handler, params_sum);
        // 查找不到记录，返回0
        if (sumMoney == null || sumMoney == 0.0) {
            return 0;
        }

        String orederDateTime = (new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())).concat(String.valueOf(Math.round(new Random().nextInt(900)+100)));
        Long orederDateTimeLong=Long.parseLong(orederDateTime);
        Object[] params_insert = {
                orederDateTimeLong, sumMoney, new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date()), user // 订单日期
        };
        String insertSql = "insert into `order` values(?,?,?,?)";
        int updateFlag = runner.update(insertSql, params_insert);
        return updateFlag;
    }

    // 查询订单信息
    @Override
    public List<Order> queryUserOrder(String user) throws SQLException {
        String sql = "select * from `order` where `user`=?";
        BeanListHandler<Order> handler = new BeanListHandler<>(Order.class);
        Object[] params = {user};
        List<Order> query = runner.query(sql, handler, params);
        return query;
    }
}
