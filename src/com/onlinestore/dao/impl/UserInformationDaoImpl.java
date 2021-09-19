package com.onlinestore.dao.impl;

import com.onlinestore.dao.UserInformationDao;
import com.onlinestore.pojo.UserInformation;
import com.onlinestore.pojo.UserRole;
import com.onlinestore.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author 涂鏊飞tu_aofei@163.com
 * @description: 用户注册和登录 类描述
 * @create 2021-09-01 17:26
 */
public class UserInformationDaoImpl implements UserInformationDao {

    private QueryRunner runner;

    public UserInformationDaoImpl() {
        runner = JDBCUtils.getQueryRunner();
    }


    // 注册
    @Override
    public void register(UserInformation userInformation) throws SQLException {
        String sql = "insert into userInformation values(?,?,?,?,?)";
        Object[] params = {
                userInformation.getUser(),
                userInformation.getPassword(),
                userInformation.getUname(),
                userInformation.getRoleid(),
                userInformation.getStatus()
        };
        runner.update(sql, params);
    }

    @Override
    public Integer login(String user, String password) throws SQLException {
        Integer result = 0;
        String sql = "select count(*) from userInformation where `user`=? and `password`=?";
        Object[] params = {user, password};
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long count = runner.query(sql, handler, params);
        Integer state = this.getState(user);
        if (count == 1) {
            result = 1; // 成功
        } else if (state == 0) { //1启用 0禁用
            result = 0; // 账户被禁用
        } else {
            result = -1;// 未找到
        }
        return result;
    }

    // 查询用户状态 1启用 0禁用
    @Override
    public Integer getState(String account) throws SQLException {
        String sql = "select `status` from userInformation where user=?";
        Object[] prams = {account};
        ScalarHandler<Integer> handler = new ScalarHandler<>();
        Integer state = runner.query(sql, handler, prams);
        if (state == null) {
            return 0;
        }
        return state;
    }
    // 获取用户菜单
    @Override
    public String[] getMenu(String account) throws SQLException {
        String sql = "select `menulist` from userrole where `roleid`=(select `roleid` from userInformation where `user`=?)";
        Object[] params = {account};
        ScalarHandler<String> handler = new ScalarHandler();
        String query = runner.query(sql, handler, params);
        return query.split("\\|");
    }
}
