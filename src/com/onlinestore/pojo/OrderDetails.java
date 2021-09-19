package com.onlinestore.pojo;

import lombok.*;

/**
 * @author 涂鏊飞tu_aofei@163.com
 * @description: 订单明细表映射 类描述
 * @create 2021-09-01 17:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {
    private Integer orderDetailsid;
    private String orderid;
    private Integer commodityid;
    private String commodityname;
    private Double price;
    private Integer amount;
    private Double money;


    @Override
    public String toString() {
        return
                "订单明细编号=" + orderDetailsid +
                        ", 订单编号=" + orderid +
                        ", 商品id=" + commodityid +
                        ", 商品名称='" + commodityname + '\'' +
                        ", 价格=" + price +
                        ", 数量=" + amount +
                        ", 金额=" + money;
    }
}
