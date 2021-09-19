package com.onlinestore.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author 涂鏊飞tu_aofei@163.com
 * @description: 数据库操作工具类 类描述
 * @create 2021-09-01 17:18
 */
public class JDBCUtils {
    private static DataSource ds;
    static {
        try {
            //1.加载配置文件
            Properties pro = new Properties();
            pro.load(JDBCUtils.class.getClassLoader().getResourceAsStream("db.properties"));
            //2.获取DataSource
            ds = DruidDataSourceFactory.createDataSource(pro);
        }
        catch (Exception e){
            ds = null;
        }
    }

    private static Connection getConnection(){
        Connection con;
        try {
            con = ds.getConnection();
        }
        catch (Exception ex){
            con = null;
        }
        return con;
    }
    /* 获取 QueryRunner */
    public static QueryRunner getQueryRunner(){
        QueryRunner runner = new QueryRunner(ds);
        return runner;
    }

    public static CallableStatement getStatement(String sql) throws Exception{
        CallableStatement call = getConnection().prepareCall(sql);
        return call;
    }

}