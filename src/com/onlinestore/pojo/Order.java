package com.onlinestore.pojo;

import lombok.*;

import java.util.Date;

/**
 * @author 涂鏊飞tu_aofei@163.com
 * @description: 订单表映射 类描述
 * @create 2021-09-01 17:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String orderid;
    private Double summoney;
    private Date orderdate;
    private String user;



    @Override
    public String toString() {
        return
                "订单编号=" + orderid +
                ", 总金额=" + summoney +
                ", 下单日期=" + orderdate +
                ", 用户账号='" + user + '\'' ;
    }
}
