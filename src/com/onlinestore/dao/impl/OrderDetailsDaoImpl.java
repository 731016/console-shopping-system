package com.onlinestore.dao.impl;

import com.onlinestore.dao.OrderDetailsDao;
import com.onlinestore.pojo.Order;
import com.onlinestore.pojo.OrderDetails;
import com.onlinestore.pojo.ShopCar;
import com.onlinestore.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 涂鏊飞tu_aofei@163.com
 * @description: TODO 类描述
 * @create 2021-09-03 11:05
 */
public class OrderDetailsDaoImpl implements OrderDetailsDao {
    private QueryRunner runner;

    public OrderDetailsDaoImpl() {
        runner = JDBCUtils.getQueryRunner();
    }

    // 生成订单详细表
    @Override
    public Integer addOrderDetails(String account) throws SQLException {
        String polymericQuerySql = "select o.orderid,s.commodityid,s.commodityname,s.price,s.amount,s.money from `shopCar` s inner join `order` o on s.`user`=o.`user` where o.`user`=?";
        Object[] polymericQueryParams = {account};
        BeanListHandler<OrderDetails> handler = new BeanListHandler<>(OrderDetails.class);
        // 获取订单详细信息映射
        List<OrderDetails> queryDetails = runner.query(polymericQuerySql, handler, polymericQueryParams);

        String insertSql = null;
        Object[] insertSqlParams = null;
        int updateFlag = 0;
        // 循环插入数据给订单详细表
        for (OrderDetails queryDetail : queryDetails) {
            insertSql = "insert into `orderdetails` values(default,?,?,?,?,?,?)";
            insertSqlParams = new Object[]{
                    queryDetail.getOrderid(),
                    queryDetail.getCommodityid(),
                    queryDetail.getCommodityname(),
                    queryDetail.getPrice(),
                    queryDetail.getAmount(),
                    queryDetail.getMoney()
            };
            updateFlag += runner.update(insertSql, insertSqlParams);
        }
        return updateFlag;
    }

    // 查询订单明细 error
    @Override
    public List<OrderDetails> queryAllOrderDetails(String account) throws SQLException {
//        List<Order> orders = new OrderDaoImpl().queryUserOrder(account);
//        String sql = "";
//        Object[] params={};
//        List<List<OrderDetails>> lists= new ArrayList<>();
//        BeanListHandler<OrderDetails> handler=new BeanListHandler<>(OrderDetails.class);
//        for (Order order : orders) {
//            sql = "select * from `order` where `orderid`=?";
//            params= new Object[]{order.getOrderid()};
//            System.out.println(params); // 空
//            System.out.println(order);
//            List<OrderDetails> query = runner.query(sql, handler, params);
//            lists.add(query);
//        }
        String sql= "select *from `order` o inner join `orderdetails` ord on o.orderid=ord.orderid";
        BeanListHandler<OrderDetails> handler=new BeanListHandler<>(OrderDetails.class);
        List<OrderDetails> query = runner.query(sql, handler);
        return query;
    }
}
