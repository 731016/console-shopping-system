package com.onlinestore.dao.impl;

import com.onlinestore.dao.CommodityInformationDao;
import com.onlinestore.pojo.CommodityInformation;
import com.onlinestore.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author 涂鏊飞tu_aofei@163.com
 * @description: 展示商品列表 类描述
 *  删除 商品id 自编号 使用truncate
 * @create 2021-09-02 14:55
 */
public class CommodityInformationDaoImpl implements CommodityInformationDao {
    private QueryRunner runner;

    public CommodityInformationDaoImpl() {
        runner = JDBCUtils.getQueryRunner();
    }

    @Override
    public List<CommodityInformation> queryAll() throws SQLException {
        String sql = "select * from commodityInformation";
        BeanListHandler<CommodityInformation> handler = new BeanListHandler<>(CommodityInformation.class);
        List<CommodityInformation> list = runner.query(sql, handler);
        return list;
    }

    @Override
    public Map<String, Integer> quaryAllCommodityId(Integer orderid) throws SQLException {
        String sql = "select `commodityid` from commodityInformation";
        MapListHandler handler = new MapListHandler();
        List<Map<String, Object>> query = runner.query(sql, handler);
        Map<String, Integer> returnMap = new HashMap<>();
        for (int i = 0; i < query.size(); i++) {
            for (String s : query.get(i).keySet()) {
                // 查找key加上随机数，value为商品编号
                returnMap.put(s.concat(String.valueOf(new Random().nextInt())), Integer.parseInt(query.get(i).get(s).toString()));
            }
        }
        return returnMap;
    }
}
