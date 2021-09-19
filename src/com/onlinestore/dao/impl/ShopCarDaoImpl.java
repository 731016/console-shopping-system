package com.onlinestore.dao.impl;

import com.onlinestore.dao.ShopCarDao;
import com.onlinestore.pojo.CommodityInformation;
import com.onlinestore.pojo.ShopCar;
import com.onlinestore.pojo.UserInformation;
import com.onlinestore.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 涂鏊飞tu_aofei@163.com
 * @description: 购物车 类描述
 * @create 2021-09-02 15:40
 */
public class ShopCarDaoImpl implements ShopCarDao {
    private QueryRunner runner;

    public ShopCarDaoImpl() {
        runner = JDBCUtils.getQueryRunner();
    }
    // 购物车添加
    @Override
    public Integer updateShopCar(Integer no, String account, Integer count) throws SQLException {
        String sql = "select * from commodityInformation where commodityid=?";
        Object[] params = {no};
        BeanHandler<CommodityInformation> handler = new BeanHandler<>(CommodityInformation.class);
        CommodityInformation query = runner.query(sql, handler, params);

        String updatesql = "insert into `shopCar` values(default,?,?,?,?,?,?)";
        Object[] updateParams = {
                account,
                no,
                query.getCommodityname(),
                query.getPrice(),
                count,
                query.getPrice() * count
        };
        int updateFlag = runner.update(updatesql, updateParams);
        return updateFlag;
    }
    // 查询用户购物车
    @Override
    public List<ShopCar> queryUserShopCar(String account) throws SQLException {
        String sql = "select * from shopCar where `user`=?";
        Object[] params={account};
        BeanListHandler<ShopCar> handler = new BeanListHandler<>(ShopCar.class);
        List<ShopCar> query = runner.query(sql, handler,params);
        return query;
    }
    // 提交订单后删除购物车记录
    @Override
    public Integer deleteUserShopCar(String account) throws SQLException {
        String sql="delete from shopCar where `user`=?";
        Object[] params={account};
        int updateFlag = runner.update(sql, params);
        return updateFlag;
    }
}
