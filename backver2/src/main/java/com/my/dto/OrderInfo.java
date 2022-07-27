package com.my.dto;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

/*
 * 주문기본정보
 */
public class OrderInfo {
  private int orderNo;
  private String orderId;
  @JsonFormat(pattern = "yy/MM/dd", timezone = "Asia/Seoul")
  private Date orderDate;
  private List<OrderLine> orderLines; // OrderInfo has an OrderLine relationship

  public OrderInfo() {}

  public OrderInfo(int orderNo, String orderId, Date orderDate, List<OrderLine> orderLines) {
    this.orderNo = orderNo;
    this.orderId = orderId;
    this.orderDate = orderDate;
    this.orderLines = orderLines;
  }

  public int getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(int orderNo) {
    this.orderNo = orderNo;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Date getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }

  public List<OrderLine> getOrderLines() {
    return orderLines;
  }

  public void setOrderLines(List<OrderLine> orderLines) {
    this.orderLines = orderLines;
  }

  @Override
  public String toString() {
    return "OrderInfo [orderNo=" + orderNo + ", orderId=" + orderId + ", orderDate=" + orderDate
        + ", orderLines=" + orderLines + "]";
  }
}
