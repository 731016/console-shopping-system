# 控制台购物系统

# 介绍
JavaSE+JDBC设计一个使用控制台操作的简易购物系统

# 需要导入的包
包括：阿里云的德鲁伊数据库连接池，测试包，lombok自动设置构造、set/get方法，数据库连接驱动[数据库对应版本8.0+]
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


## dao层
### （1）UserInformationDao接口
 **用户信息接口** 
`public interface UserInformationDao`

#### 抽象方法
```java
用户注册
public void register(UserInformation userInformation)
```
```java
用户登录
public Integer login(String user,String password)
```
```java
获取用户状态 1-启动，0-禁用
public Integer getState(String user)
```
```java
获取用户操作菜单，三个用户对应着不同的操作
public String[] getMenu(String account)
```

### （2）CommodityInformationDao接口
`public interface CommodityInformationDao`
#### 抽象方法

### （3）

#### 抽象方法

### （4）

#### 抽象方法

### （5）

#### 抽象方法


## service层
### CommodityInformationDao接口
`public interface CommodityInformationDao`

#### 抽象方法
```java
查询所有商品
public List<CommodityInformation> queryAll()
```
```java
查询所有商品编号
public Map<String,Integer> quaryAllCommodityId(Integer commodityid)
```

## service层

## ui层