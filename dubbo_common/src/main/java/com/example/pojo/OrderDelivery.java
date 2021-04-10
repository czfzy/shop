package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "order_delivery")
public class OrderDelivery extends BaseEntity {
    private Long subId;
    private String reciveName;
    private Integer reciveProvince;
    private Integer reciveCity;
    private Integer reciveArea;
    private String reciveAddress;
    private String reciveTel;
    private String reciveEmail;
    private String postId;
    private Byte postStatus;
    private BigDecimal postFee;
    private Date postTime;
    private Date deliveryTime;
    private Byte status;

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public String getReciveName() {
        return reciveName;
    }

    public void setReciveName(String reciveName) {
        this.reciveName = reciveName == null ? null : reciveName.trim();
    }

    public Integer getReciveProvince() {
        return reciveProvince;
    }

    public void setReciveProvince(Integer reciveProvince) {
        this.reciveProvince = reciveProvince;
    }

    public Integer getReciveCity() {
        return reciveCity;
    }

    public void setReciveCity(Integer reciveCity) {
        this.reciveCity = reciveCity;
    }

    public Integer getReciveArea() {
        return reciveArea;
    }

    public void setReciveArea(Integer reciveArea) {
        this.reciveArea = reciveArea;
    }

    public String getReciveAddress() {
        return reciveAddress;
    }

    public void setReciveAddress(String reciveAddress) {
        this.reciveAddress = reciveAddress == null ? null : reciveAddress.trim();
    }

    public String getReciveTel() {
        return reciveTel;
    }

    public void setReciveTel(String reciveTel) {
        this.reciveTel = reciveTel;
    }

    public String getReciveEmail() {
        return reciveEmail;
    }

    public void setReciveEmail(String reciveEmail) {
        this.reciveEmail = reciveEmail == null ? null : reciveEmail.trim();
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId == null ? null : postId.trim();
    }

    public Byte getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(Byte postStatus) {
        this.postStatus = postStatus;
    }

    public BigDecimal getPostFee() {
        return postFee;
    }

    public void setPostFee(BigDecimal postFee) {
        this.postFee = postFee;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}