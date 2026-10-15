package com.shop.controller;
import com.shop.model.Product; import com.shop.repo.ProductRepository;
import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@RestController @RequestMapping("/api")
public class ProductController {
 private final ProductRepository products; public ProductController(ProductRepository p){products=p;}
 @GetMapping("/products") public List<Product> all(@RequestParam(required=false) String q,@RequestParam(required=false) String category){
  return products.findAll().stream().filter(p->q==null||p.getName().toLowerCase().contains(q.toLowerCase())||(p.getDescription()!=null&&p.getDescription().toLowerCase().contains(q.toLowerCase())))
   .filter(p->category==null||category.isBlank()||category.equalsIgnoreCase(p.getCategory())).toList();
 }
 @GetMapping("/products/{id}") public Product one(@PathVariable Long id){return products.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));}
 @GetMapping("/categories") public List<String> categories(){return products.findAll().stream().map(Product::getCategory).filter(java.util.Objects::nonNull).distinct().toList();}
}
