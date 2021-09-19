package com.onlinestore.ui;

import com.onlinestore.dao.UserInformationDao;
import com.onlinestore.pojo.*;
import com.onlinestore.service.*;
import com.onlinestore.service.impl.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.junit.Test;

import javax.management.relation.InvalidRoleValueException;

/**
 * @author 涂鏊飞tu_aofei@163.com
 * @description: 测试类 类描述
 * @create 2021-09-01 17:27
 */
public class TestDemo {
    private static Scanner in;
    private static UserInformationService service;
    private static CommodityInformationService service_commodity;
    private static ShopCarService shopCarService;
    private static OrderService orderService;
    private static OrderDetailsService orderDetailsService;

    static {
        in = new Scanner(System.in);
        service = new UserInformationServiceImpl();
        service_commodity = new CommodityInformationServiceImpl();
        shopCarService = new ShopCarServiceImpl();
        orderService = new OrderServiceImpl();
        orderDetailsService = new OrderDetailsServiceImpl();
    }

    @Test
    public void testDemo() {
        new TestDemo().start();
    }

    private void start() {
        System.out.println("1. 登录 | 2. 注册");
        System.out.println("请选择：");
        String choose = in.nextLine();
        if (choose.equals("1")) {
            login();
        } else if (choose.equals("2")) {
            System.out.println("1. 注册买家");
            System.out.println("2. 注册商家");
            String chooseTwoMenu = in.nextLine();
            if (chooseTwoMenu.equals("1")) {
                registerBuyer();
            } else if (chooseTwoMenu.equals("2")) {
                registerBusiness();
            } else if (!choose.equals("1") || !choose.equals("2")) {
                throw new InputMismatchException("输入类型不匹配，请输入正确的数字！");
            }
        } else if (!choose.equals("1") || !choose.equals("2")) {
            throw new InputMismatchException("输入类型不匹配，请输入正确的数字！");
        }
    }

    private static void registerBuyer() {
        try {
            System.out.println("请输入新账号：");
            String account = in.next();
            System.out.println("请输入密码：");
            String pwd = in.next();
            System.out.println("请输入姓名：");
            String uname = in.next();
            service.register(new UserInformation(account, pwd, uname, 2, 1));
            System.out.println("买家注册成功!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void registerBusiness() {
        try {
            System.out.println("请输入新账号：");
            String account = in.next();
            System.out.println("请输入密码：");
            String pwd = in.next();
            System.out.println("请输入姓名：");
            String uname = in.next();
            service.register(new UserInformation(account, pwd, uname, 1, 1));
            System.out.println("商家注册成功!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void login() {
        try {
            System.out.println("请输入账号：");
            String account = in.next();
            System.out.println("请输入密码：");
            String pwd = in.next();
            Integer loginFlag = service.login(account, pwd);
            if (loginFlag == 1) {
                System.out.println("登陆成功!");
                flow(account);
            } else if (loginFlag == 0) {
                System.out.println("您已被禁用!");
            } else {
                System.out.println("账号或密码错误!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //执行流程
    private static void flow(String account) throws Exception {
        showHomeMenu(account);
        System.out.println("请选择菜单项[0 退出]：");
        String commChoose = in.next();
        switch (commChoose) {
            case "商品列表":
                showCommodityList();
                shopping(account);
                // 购物
                System.out.println("选购完毕！，您的购物车的商品如下：");
                queryShopCar(account);
                System.out.println("是否结算?[y/n]");
                commitOrder(account); //提交订单
                break;
            case "查看购物车":
                System.out.println("您的购物车的商品如下：");
                queryShopCar(account);
                System.out.println("是否结算?[y/n]");
                commitOrder(account); //提交订单
                break;
            case "个人信息管理":
                break;
            case "0":
                System.exit(1);
            default:
                throw new InputMismatchException("输入类型不匹配，请输入正确的字符串！");
        }
    }

    // 提交订单
    private static void commitOrder(String account) throws Exception {
        String settleAccounts = in.next();
        if (settleAccounts.equals("y") || settleAccounts.equals("Y")) {
            //结算
            Integer orderInsertFlag = orderService.updateOrder(account);//提交订单
            if (orderInsertFlag > 0) {
                System.out.println("订单已提交，订单数目:" + orderInsertFlag);
            } else {
                System.out.println("您的购物车为空！，无法生成订单！");
            }
            // 生成订单明细表
            Integer orderDetailsUpdateFlag = orderDetailsService.addOrderDetails(account);
            if (orderDetailsUpdateFlag > 0) {
                System.out.println("订单明细已生成，数目:" + orderDetailsUpdateFlag);
            }
            // 删除购物车记录
            Integer deleteUserShopCarFlag = shopCarService.deleteUserShopCar(account);
            if (deleteUserShopCarFlag > 0) {
                System.out.println("用户" + account + "购物车记录已删除，更新记录条数:" + deleteUserShopCarFlag);
            }
            // 查看订单
            queryOrder(account);

        } else {
            flow(account);
        }
    }

    // 查看订单，明细表
    private static void queryOrder(String account) throws Exception {
        System.out.println("1. 查看您的所有订单");
        System.out.println("2. 查看您的所有订单明细");
        System.out.println("3. 返回主界面");
        Integer orderChoose = in.nextInt();
        switch (orderChoose) {
            case 1:
                System.out.println("已提交的订单如下：");
                List<Order> orderList = orderService.queryUserOrder(account);
                for (Order order : orderList) {
                    System.out.println(order);
                }
                queryOrder(account);// 回调
                break;
            case 2:
                System.out.println("已提交的订单详情如下：");
                List<OrderDetails> lists = orderDetailsService.queryAllOrderDetails(account);
                for (OrderDetails list : lists) {
                    System.out.println(list);
                }
                queryOrder(account);// 回调
                break;
            case 3:
                flow(account); // 返回主页面
                break;
            default:
                System.out.println("输入错误！，请重新输入：");
                queryOrder(account);
        }


    }

    // 查看购物车
    private static void queryShopCar(String account) throws SQLException {
        List<ShopCar> shopCars = shopCarService.queryUserShopCar(account);
        shopCars.forEach(System.out::println);
    }

    // 购买商品
    private static void shopping(String account) throws Exception {
        while (true) {
            System.out.println("请选择中意的商品编号...");
            Integer no = in.nextInt();
            Map<String, Integer> maps = service_commodity.quaryAllCommodityId(no);
            if (maps.isEmpty() || !maps.containsValue(no)) {
//                throw new NullPointerException("商品编号不存在！");
                System.out.println("商品编号不存在！");
                shopping(account); // 继续购物
            }
            System.out.println("请选择要购买的商品数量...");
            Integer count = in.nextInt();
            Integer flag = shopCarService.updateShopCar(no, account, count);
            System.out.println("加入购物车" + flag + "件商品");
            System.out.println("是否继续购物[y/n]");
            String continues = in.next();
            if (!(continues.equals("y") || continues.equals("Y"))) {
                break;
            }
            showCommodityList();
        }
    }

    // 展示首页
    private static void showHomeMenu(String account) throws SQLException {
        String[] menu = service.getMenu(account);
        System.out.println("---------------------------------------");
        System.out.println("-----------欢迎来到网购系统首页----------");
        for (int i = 0; i < menu.length; i++) {
            System.out.println((i + 1) + "." + menu[i]);
        }
        System.out.println("---------------------------------------");
    }

    // 商品列表
    private static void showCommodityList() throws SQLException {
        List<CommodityInformation> commodityInformations = service_commodity.queryAll();
        for (CommodityInformation ci : commodityInformations) {
            System.out.println("------------------------------------------------------------------------------------------------------------------");
            System.out.println(ci);
        }
    }
}
