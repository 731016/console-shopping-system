package com.onlinestore.service.impl;

import com.onlinestore.dao.CommodityInformationDao;
import com.onlinestore.dao.impl.CommodityInformationDaoImpl;
import com.onlinestore.pojo.CommodityInformation;
import com.onlinestore.service.CommodityInformationService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 涂鏊飞tu_aofei@163.com
 * @description: TODO 类描述
 * @create 2021-09-02 15:00
 */
public class CommodityInformationServiceImpl implements CommodityInformationService {
    private CommodityInformationDao commodityInformationDao;

    public CommodityInformationServiceImpl() {
        commodityInformationDao = new CommodityInformationDaoImpl();
    }

    @Override
    public List<CommodityInformation> queryAll() throws SQLException {
        return commodityInformationDao.queryAll();
    }

    @Override
    public Map<String, Integer> quaryAllCommodityId(Integer commodityid) throws SQLException {
        return commodityInformationDao.quaryAllCommodityId(commodityid);
    }
}
