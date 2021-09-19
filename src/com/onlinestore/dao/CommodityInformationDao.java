package com.onlinestore.dao;

import com.onlinestore.pojo.CommodityInformation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CommodityInformationDao {
    // 查询所有商品
    public List<CommodityInformation> queryAll() throws SQLException;
    // 查询所有商品编号
    public Map<String,Integer> quaryAllCommodityId(Integer commodityid) throws SQLException;
}
