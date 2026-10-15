package com.shop.config;
import com.shop.model.*; import com.shop.repo.*;
import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration; import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;
@Configuration
public class DataSeeder {
 @Bean CommandLineRunner seed(UserRepository users,ProductRepository products,CategoryRepository categories,PasswordEncoder encoder){
  return args->{
   if(!users.existsByEmail("admin@shop.local"))users.save(new AppUser("Shop Admin","admin@shop.local",encoder.encode("Admin@123"),Role.ADMIN));
   if(!users.existsByEmail("customer@shop.local"))users.save(new AppUser("Demo Customer","customer@shop.local",encoder.encode("Customer@123"),Role.USER));
   if(categories.count()==0){categories.save(new Category("Electronics","Devices and accessories"));categories.save(new Category("Lifestyle","Everyday essentials"));categories.save(new Category("Home","Comfort for your home"));}
   if(products.count()==0){
    products.save(new Product("Studio Headphones","Immersive audio with a comfortable over-ear fit.",new BigDecimal("2499"),"https://images.unsplash.com/photo-1505740420928-5e560c06d30e?auto=format&fit=crop&w=900&q=85","Electronics",25));
    products.save(new Product("Minimal Sneakers","Lightweight everyday sneakers with a clean silhouette.",new BigDecimal("3199"),"https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&w=900&q=85","Lifestyle",18));
    products.save(new Product("Smart Watch","Track your day with a bright display and long battery life.",new BigDecimal("4299"),"https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&w=900&q=85","Electronics",14));
    products.save(new Product("Desk Lamp","Warm ambient lighting for your workspace.",new BigDecimal("1599"),"https://images.unsplash.com/photo-1507473885765-e6ed057f782c?auto=format&fit=crop&w=900&q=85","Home",30));
    products.save(new Product("Everyday Backpack","A roomy, durable bag for work and travel.",new BigDecimal("1899"),"https://images.unsplash.com/photo-1553062407-98eeb64c6a62?auto=format&fit=crop&w=900&q=85","Lifestyle",21));
    products.save(new Product("Ceramic Coffee Set","A simple, modern set for your morning ritual.",new BigDecimal("999"),"https://images.unsplash.com/photo-1514228742587-6b1558fcca3d?auto=format&fit=crop&w=900&q=85","Home",40));
   }
  };
 }
}
