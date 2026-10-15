package com.shop.controller;
import com.shop.model.Product; import com.shop.repo.ProductRepository;
import jakarta.validation.Valid; import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal; import java.util.Map;
@RestController @RequestMapping("/api/admin/products")
public class AdminProductController {
 private final ProductRepository products;public AdminProductController(ProductRepository p){products=p;}
 public record ProductRequest(@NotBlank String name,String description,@NotNull @DecimalMin("0.01") BigDecimal price,String imageUrl,String category,@Min(0) int stock){}
 @PostMapping public Product create(@Valid @RequestBody ProductRequest r){return products.save(new Product(r.name(),r.description(),r.price(),r.imageUrl(),r.category(),r.stock()));}
 @PutMapping("/{id}") public Product update(@PathVariable Long id,@Valid @RequestBody ProductRequest r){
  Product p=products.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));p.setName(r.name());p.setDescription(r.description());p.setPrice(r.price());p.setImageUrl(r.imageUrl());p.setCategory(r.category());p.setStock(r.stock());return products.save(p);
 }
 @DeleteMapping("/{id}") public Map<String,String> delete(@PathVariable Long id){if(!products.existsById(id))throw new ResponseStatusException(HttpStatus.NOT_FOUND);products.deleteById(id);return Map.of("message","Product deleted");}
}
