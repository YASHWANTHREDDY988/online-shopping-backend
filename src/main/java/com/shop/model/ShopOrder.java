package com.shop.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity @Table(name="shop_orders")
public class ShopOrder {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private Long userId;
 private String customerName;
 private String customerEmail;
 @Column(length=4000) private String items;
 private String status="CONFIRMED";
 private String paymentMethod="Demo payment";
 private String shippingAddress;
 private LocalDateTime createdAt=LocalDateTime.now();
 private BigDecimal total=BigDecimal.ZERO;
 public ShopOrder(){}
 public ShopOrder(Long uid,String name,String email,String items,String address,BigDecimal total){userId=uid;customerName=name;customerEmail=email;this.items=items;shippingAddress=address;this.total=total;}
 public Long getId(){return id;} public Long getUserId(){return userId;} public String getCustomerName(){return customerName;}
 public String getCustomerEmail(){return customerEmail;} public String getItems(){return items;} public String getStatus(){return status;}
 public void setStatus(String v){status=v;} public String getPaymentMethod(){return paymentMethod;} public void setPaymentMethod(String v){paymentMethod=v;}
 public String getShippingAddress(){return shippingAddress;} public LocalDateTime getCreatedAt(){return createdAt;} public BigDecimal getTotal(){return total;}
}
