#   :tw-1f308: 控制台购物系统

# 介绍
JavaSE+JDBC设计一个使用控制台操作的简易购物系统

#  :heavy_exclamation_mark: 源码
直接下载本文件夹即可


# 需要导入的包
包括：阿里云的德鲁伊数据库连接池，测试包，lombok自动设置构造、set/get方法，数据库连接驱动[数据库对应版本8.0+]<br>
 **注意** ：数据库驱动版本不对，会导致数据库连接失败，触发SQLExceotipn异常
![输入图片说明](https://images.gitee.com/uploads/images/2021/0919/232453_945fd10e_8254421.png "jar.png")

# 数据库设计
```sql
用户信息表（账号、密码、姓名、角色id、状态）
用户角色表（角色id、角色名称、菜单列表）
商品信息表（商品id、名称、类型id、品牌、价格、生产日期、失效日期）
商品类型表（类型id、类型名称）
购物车表（自编号、用户账号、商品id、商品名称、价格、数量、金额）
订单表（订单编号、总金额、下单日期、用户账号）
订单明细表（自编号、订单编号、商品id、商品名称、价格、数量、金额）
```

## 初始化表格
```sql
-- 用户信息表（账号、密码、姓名、角色id、状态）
#userInformation（{4}user、password、uname、[1]roleid、status）
drop table if exists userInformation;
create table userInformation(
  `user` varchar(18) primary key,
  `password` varchar(255) not null,
  uname varchar(12) not null unique,
  roleid int not null,
  `status` int not null
)default charset=utf8;


-- 用户角色表（角色id、角色名称、菜单列表）
#userRole（[1]roleid、rolename、menulist）
drop table if exists userRole;
create table userRole(
  roleid int primary key,
  rolename varchar(12) not null unique,
  menulist varchar(255) not null
)default charset=utf8;

-- 商品信息表（{5}商品id、名称、类型id、品牌、价格、生产日期、失效日期）
#commodityInformation（commodityid、commodityname、[2]typeid、brand、price、productiondate、expirationdate）
drop table if exists commodityInformation;
create table commodityInformation(
  commodityid int auto_increment primary key,
  commodityname varchar(255) not null unique,
  typeid int not null,
  brand varchar(255) not null,
  price double(12,2) not null,
  productiondate date not null,
  expirationdate date not null
)default charset=utf8;


-- 商品类型表（类型id、类型名称）
#commodityTypes（[2]typeid、commoditypename）
drop table if exists commodityTypes;
create table commodityTypes(
  typeid int primary key,
  commoditypename varchar(255) not null unique
)default charset=utf8;

-- 购物车表（自编号、用户账号、商品id、商品名称、价格、数量、金额）
#shopCar（shopcarid、{4}user、{5}commodityid、commodityname、price、amount、money）
drop table if exists shopCar;
create table shopCar(
  shopcarid int auto_increment primary key,
  `user` varchar(18) not null,
  commodityid int not null,
  commodityname varchar(255) not null,
  price double(12,2) not null,
  amount int not null,
  money double(12,2) not null
)default charset=utf8;


-- 订单表（订单编号、总金额、下单日期、用户账号）
#order（[3]orderid、summoney、orderdate、{4}user）
drop table if exists `order`;
create table `order`(
  orderid varchar(100) primary key,
  summoney double(12,2) not null,
  orderdate date not null,
  `user` varchar(18) not null
)default charset=utf8;

-- 订单明细表（自编号、订单编号、商品id、商品名称、价格、数量、金额）
#orderDetails（orderDetailsid、[3]orderid、{5}commodityid、commodityname、price、amount、money）
drop table if exists orderDetails;
create table orderDetails(
  orderDetailsid int auto_increment primary key,
  orderid varchar(100) not null,
  commodityid int not null,
  commodityname varchar(255) not null,
  price double(12,2) not null,
  amount int not null,
  money double(12,2) not null
)default charset=utf8;

/*
# 关联用户角色表的角色id
alter table userInformation add constraint fk_roleid foreign key(roleid) references userRole(userRole);

# 关联商品类型表的类型id
alter table commodityInformation add constraint fk_typeid foreign key(typeid) references commodityTypes(typeid);

# 关联用户信息表的用户名
alter table shopCar add constraint fk_user foreign key(`user`) references userInformation(`user`);
# 关联商品信息表的商品id
alter table shopCar add constraint fk_commodityid foreign key(commodityid) references commodityInformation(commodityid);


# 关联购物车表的用户名
alter table `order` add constraint fk_user foreign key(`user`) references shopCar(`user`);

# 关联购物车表的商品id
alter table orderDetails add constraint fk_commodityid foreign key(commodityid) references shopCar(commodityid);
*/
```

## 初始化数据
```sql
/*用户角色表（角色id、角色名称、菜单列表）
0 超级管理员
1 卖家
2 买家
3 黑名单
userRole（[1]roleid、rolename、menulist）*/
insert into userRole values(0,'超级管理员','商品管理|订单管理|用户管理|商品列表|购物|个人信息管理');
insert into userRole values(1,'商家','商品管理|订单管理|商品列表|个人信息管理');
insert into userRole values(2,'买家','商品列表|查看购物车|个人信息管理');

/*用户信息表（账号、密码、姓名、角色id、状态）
userInformation（[4]user、password、uname、[1]roleid、status）*/
insert into userInformation values('root','root','马化腾',0,1);
insert into userInformation values('admin1','123456','苹果',1,1);
insert into userInformation values('admin2','123456','鸿星尔克',1,1);
insert into userInformation values('admin3','123456','妮维雅',1,1);
insert into userInformation values('buyer1','123','张三',2,1);
insert into userInformation values('buyer2','123','李四',2,1);
insert into userInformation values('buyer3','123','王五',2,1);

/*商品类型表（类型id、类型名称）
commodityTypes（[2]typeid、commoditypename）*/
insert into commodityTypes values(1001,'数码');
insert into commodityTypes values(1002,'服装');
insert into commodityTypes values(1003,'洗护');

/*商品信息表（商品id、名称、类型id、品牌、价格、生产日期、失效日期）
commodityInformation（[3]commodityid、commodityname、[2]typeid、brand、price、productiondate、expirationdate）*/
insert into commodityInformation values(default,'iPhoneX',1001,'苹果',8900,date_sub(now(),interval 1 year),date_add(now(),interval 99 year));
insert into commodityInformation values(default,'iPad2021',1001,'苹果',2890,date_sub(now(),interval 90 day),date_add(now(),interval 90 year));
insert into commodityInformation values(default,'AirPods',1001,'苹果',1200,date_sub(now(),interval 2 year),date_add(now(),interval 20 year));

insert into commodityInformation values(default,'t恤',1002,'鸿星尔克',120.5,date_sub(now(),interval 60 day),date_add(now(),interval 10 year));
insert into commodityInformation values(default,'黑裤子',1002,'鸿星尔克',200,date_sub(now(),interval 70 day),date_add(now(),interval 10 year));
insert into commodityInformation values(default,'鞋子',1002,'鸿星尔克',230,date_sub(now(),interval 80 day),date_add(now(),interval 10 year));

insert into commodityInformation values(default,'洗面奶',1001,'妮维雅',8900,date_sub(now(),interval 90 day),date_add(now(),interval 1 year));
insert into commodityInformation values(default,'护发素',1001,'妮维雅',8900,date_sub(now(),interval 90 day),date_add(now(),interval 1 year));
insert into commodityInformation values(default,'眼霜',1001,'妮维雅',8900,date_sub(now(),interval 90 day),date_add(now(),interval 1 year));

select * from userRole;
select * from userInformation;
select * from commodityTypes
select * from commodityInformation;
```
## 用户信息
![输入图片说明](https://images.gitee.com/uploads/images/2021/0920/003437_6e8b126a_8254421.png "用户信息.png")
## 用户角色
![输入图片说明](https://images.gitee.com/uploads/images/2021/0920/003451_ecb39af5_8254421.png "用户角色.png")
## 商品类型
![输入图片说明](https://images.gitee.com/uploads/images/2021/0920/003458_bac47e93_8254421.png "商品类型.png")
## 商品信息
![输入图片说明](https://images.gitee.com/uploads/images/2021/0920/003505_f1e86819_8254421.png "商品信息.png")

# 主要功能

 **用户角色：超级管理员，买家，卖家** 
1. 可注册，登录
2. 暂时只对 **买家** 进行操作！
3. 买家可查看商品，加入购物车，结算，查看订单和订单明细。。。

# 系统架构
分为5层，分别为：
1.  **dao** 层 数据访问层，对数据库进行操作
2.  **service** 层 业务逻辑层
3.  **utils** 层 数据库工具类，连接数据库
4.  **ui** 层 测试代码
5.  **pojo** 层 数据库映射表

# 界面展示
![买家注册功能](https://images.gitee.com/uploads/images/2021/0920/002507_b4f17af1_8254421.png "1买家注册功能.png")
![商家注册功能](https://images.gitee.com/uploads/images/2021/0920/002523_4982c0ba_8254421.png "2商家注册功能.png")
![商家登录首页](https://images.gitee.com/uploads/images/2021/0920/002531_192c9dde_8254421.png "3商家登录首页.png")
![买家登录首页](https://images.gitee.com/uploads/images/2021/0920/002540_0313519b_8254421.png "4买家登录首页.png")
![买家查看商品列表](https://images.gitee.com/uploads/images/2021/0920/002547_d2ec8d85_8254421.png "5买家查看商品列表.png")
![买家加入购物车-继续购物](https://images.gitee.com/uploads/images/2021/0920/002554_476376da_8254421.png "6买家加入购物车-继续购物.png")
![买家购物完成](https://images.gitee.com/uploads/images/2021/0920/002604_5cf26741_8254421.png "7买家购物完成.png")
![买家结算订单](https://images.gitee.com/uploads/images/2021/0920/002655_9a14cae4_8254421.png "买家结算订单.png")
![买家查看订单信息](https://images.gitee.com/uploads/images/2021/0920/002612_b861a282_8254421.png "8买家查看订单信息.png")
![买家查看订单明细](https://images.gitee.com/uploads/images/2021/0920/002620_6abe33ca_8254421.png "9买家查看订单明细.png")


## db.properties
本数据库配置文件与 **src** 同级
```properties
#通过反射获取获取包
driverClassName=com.mysql.jdbc.Driver

# localhost 本地服务器
# 3306 端口
# demo 数据库名
# &useSSL=false&serverTimezone=UTC 数据库版本5.0左右可去除，我使用的是8.0以上的版本，&作为参数的连接符
url=jdbc:mysql://localhost:3306/demo?characterEncoding=utf8&useSSL=false&serverTimezone=UTC

# 用户名
username=root

# 密码
password=root
```

## utils
使用德鲁伊连接池和QueryRunner处理数据，需要导包

```java
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
```

## pojo层
数据库中的表对应的实体类:
 **用户信息表** [输入链接说明](https://gitee.com/LovelyHzz/console-shopping-system/blob/master/src/com/onlinestore/pojo/UserInformation.java)<br>
 **用户角色表** [输入链接说明](https://gitee.com/LovelyHzz/console-shopping-system/blob/master/src/com/onlinestore/pojo/UserRole.java)<br>
 **商品信息表** [输入链接说明](https://gitee.com/LovelyHzz/console-shopping-system/blob/master/src/com/onlinestore/pojo/CommodityInformation.java)<br>
 **商品类型表** [输入链接说明](https://gitee.com/LovelyHzz/console-shopping-system/blob/master/src/com/onlinestore/pojo/CommodityTypes.java)<br>
 **购物车表** [输入链接说明](https://gitee.com/LovelyHzz/console-shopping-system/blob/master/src/com/onlinestore/pojo/ShopCar.java)<br>
 **订单表** [输入链接说明](https://gitee.com/LovelyHzz/console-shopping-system/blob/master/src/com/onlinestore/pojo/Order.java)<br>
 **订单明细表** [输入链接说明](https://gitee.com/LovelyHzz/console-shopping-system/blob/master/src/com/onlinestore/pojo/OrderDetails.java)<br>

## dao层
###  :one: UserInformationDao接口
 **用户信息处理接口** 
`public interface UserInformationDao`

#### 抽象方法
```java
用户注册
参数：用户信息对象
返回值：无
public void register(UserInformation userInformation)
```
```java
用户登录
参数：用户账户，密码
返回值：Integer包装类型，1-成功，0-被禁用，-1 账户或密码错误
public Integer login(String user,String password)
```
```java
获取用户状态 1-启动，0-禁用
参数：用户账户
返回值：Integer包装类型
public Integer getState(String user)
```
```java
获取用户操作菜单，三个用户对应着不同的操作
参数：用户账户
返回值：字符串数组，返回被split("\\|")截取后的数组
public String[] getMenu(String account)
```

###  :two: CommodityInformationDao接口
 **商品信息处理接口** 
`public interface CommodityInformationDao`

#### 抽象方法
```java
查询所有商品
参数：无
返回值：用户信息的List集合
public List<CommodityInformation> queryAll()
```
```java
查询所有商品编号
参数：商品id
返回值：Map集合，key值无作用，value为存在的商品编号
public Map<String,Integer> quaryAllCommodityId(Integer commodityid)
```


###  :three: ShopCarDao接口
 **购物车接口** 
`public interface ShopCarDao`
#### 抽象方法
```java
加入购物车
参数：商品id，账号，商品数量
返回值：修改过的行数
public Integer updateShopCar(Integer no, String account, Integer count)
```

```java
展示购物车
参数：账号
返回值：购物车的List集合
    public List<ShopCar> queryUserShopCar(String account)
```

```java
提交订单后，删除指定用户的购物车记录
参数：账号
返回值：受影响的行数
    public Integer deleteUserShopCar(String account)
```

###  :four: OrderDao接口
 **订单处理接口** 
`public interface OrderDao`
#### 抽象方法
```java
提交订单
参数：账号
返回值：受影响的行数
    public Integer  updateOrder(String user)
```
```java
查看用户订单
参数：账号
返回值:订单的List集合
    public List<Order> queryUserOrder(String user)
```

###  :five: OrderDetailsDao接口
 **订单详情处理接口** 
`public interface OrderDetailsDao`
#### 抽象方法
```java
增加订单详细
public Integer addOrderDetails(String account)

查询所有订单， **此功能不管是哪个用户，都会查询所有用户的订单！！！** 
public List<OrderDetails> queryAllOrderDetails(String account)
```

## service层
接口和dao层相同
###  :one: UserInformationService
```java
    public void register(UserInformation userInformation) throws SQLException;
    public Integer login(String user,String password) throws SQLException;
    public String[] getMenu(String account) throws SQLException;
```
###  :two: CommodityInformationService
```java
    public List<CommodityInformation> queryAll() throws SQLException;
    public Map<String, Integer> quaryAllCommodityId(Integer commodityid) throws SQLException;
```
###  :three: ShopCarService
```java
    public Integer updateShopCar(Integer no,String account,Integer count) throws SQLException;
    public List<ShopCar> queryUserShopCar(String account) throws SQLException;
    public Integer deleteUserShopCar(String account) throws SQLException;
```
###  :four: OrderService
```java
    public Integer updateOrder(String user) throws SQLException, ParseException;
    public List<Order> queryUserOrder(String user) throws SQLException;
```
###  :five: OrderDetailsService
```java
    public Integer addOrderDetails(String account) throws SQLException;
    public List<OrderDetails> queryAllOrderDetails(String account) throws SQLException;
```

## ui层
```java
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

```