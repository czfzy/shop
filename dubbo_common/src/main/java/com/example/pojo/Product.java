package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "product")
public class Product extends BaseEntity {
    private String productName;
    private Integer shopId;
    private Integer classify1;
    private Integer classify2;
    private Integer classify3;
    private String brand;
    private String subtitle;
    private Integer status;
    private List<Goods> goodsList;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getClassify1() {
        return classify1;
    }

    public void setClassify1(Integer classify1) {
        this.classify1 = classify1;
    }

    public Integer getClassify2() {
        return classify2;
    }

    public void setClassify2(Integer classify2) {
        this.classify2 = classify2;
    }

    public Integer getClassify3() {
        return classify3;
    }

    public void setClassify3(Integer classify3) {
        this.classify3 = classify3;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }
}