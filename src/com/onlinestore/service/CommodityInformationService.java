package com.onlinestore.service;

import com.onlinestore.pojo.CommodityInformation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CommodityInformationService {
    public List<CommodityInformation> queryAll() throws SQLException;
    public Map<String, Integer> quaryAllCommodityId(Integer commodityid) throws SQLException;
}
