package com.shop.model;
import jakarta.persistence.*;
@Entity
public class Category {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true) private String name;
 private String description;
 public Category(){} public Category(String n,String d){name=n;description=d;}
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
}
