package com.onlinestore.pojo;

import lombok.*;

/**
 * @author 涂鏊飞tu_aofei@163.com
 * @description: 购物车映射 类描述
 * @create 2021-09-01 17:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCar {
    private Integer shopcarid;
    private String user;
    private Integer commodityid;
    private String commodityname;
    private Double price;
    private Integer amount;
    private Double money;
    @Override
    public String toString() {
        return
                "编号=" + shopcarid +
                ", 用户账号='" + user + '\'' +
                ", 商品id=" + commodityid +
                ", 商品名称='" + commodityname + '\'' +
                ", 价格=" + price +
                ", 数量=" + amount +
                ", 金额=" + money;
    }
}
