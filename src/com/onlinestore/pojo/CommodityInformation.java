package com.onlinestore.pojo;

import lombok.*;

import java.util.Date;

/**
 * @author 涂鏊飞tu_aofei@163.com
 * @description: 商品信息映射 类描述
 * @create 2021-09-01 17:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommodityInformation {
    private Integer commodityid;
    private String commodityname;
    private Integer typeid;
    private String brand;
    private Double price;
    private Date productiondate;
    private Date expirationdate;


    @Override
    public String toString() {
        return
                "商品id=" + commodityid +
                ", 名称='" + commodityname + '\'' +
                ", 类型id=" + typeid +
                ", 品牌='" + brand + '\'' +
                ", 价格=" + price +
                ", 生产日期=" + productiondate +
                ", 失效日期=" + expirationdate;
    }
}
