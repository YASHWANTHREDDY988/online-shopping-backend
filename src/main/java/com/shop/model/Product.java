package com.shop.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity
public class Product {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @Column(length=2000) private String description;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal price;
 private String imageUrl;
 private String category;
 private int stock=0;
 public Product(){}
 public Product(String n,String d,BigDecimal p,String img,String cat,int stock){name=n;description=d;price=p;imageUrl=img;category=cat;this.stock=stock;}
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;}
 public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;}
 public String getCategory(){return category;} public void setCategory(String v){category=v;}
 public int getStock(){return stock;} public void setStock(int v){stock=v;}
}
