package com.dubbo.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MasterOrder implements Serializable {
    /** 订单编码 */
    private String orderId;

    /** 订单类型 */
    private String orderType;

    /** 客户编码 */
    private String buyerId;

    /** 订单来源:美团，途虎,自营 */
    private String orderSource;

    /** 价格 */
    private BigDecimal orderAmount;

    /** 订单状态 */
    private String status;

    /** 支付状态 */
    private String payStatus;

    /** 支付渠道 */
    private String payChennel;

    /** 受理日期 */
    private Date recdate;

    /** 失效日期 */
    private Date endDate;

    private static final long serialVersionUID = 1L;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId == null ? null : buyerId.trim();
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource == null ? null : orderSource.trim();
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus == null ? null : payStatus.trim();
    }

    public String getPayChennel() {
        return payChennel;
    }

    public void setPayChennel(String payChennel) {
        this.payChennel = payChennel == null ? null : payChennel.trim();
    }

    public Date getRecdate() {
        return recdate;
    }

    public void setRecdate(Date recdate) {
        this.recdate = recdate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}