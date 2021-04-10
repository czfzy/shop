package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "product_ext")
public class ProductExt extends BaseEntity {
    private Long productId;
    private String introduce;
    private String extrattribute;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }

    public String getExtrattribute() {
        return extrattribute;
    }

    public void setExtrattribute(String extrattribute) {
        this.extrattribute = extrattribute == null ? null : extrattribute.trim();
    }
}