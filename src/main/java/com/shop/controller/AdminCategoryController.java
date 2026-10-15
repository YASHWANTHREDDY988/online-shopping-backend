package com.shop.controller;
import com.shop.model.Category;import com.shop.repo.CategoryRepository;
import org.springframework.http.HttpStatus;import org.springframework.web.bind.annotation.*;import org.springframework.web.server.ResponseStatusException;
import java.util.*;
@RestController @RequestMapping("/api/admin/categories")
public class AdminCategoryController {
 private final CategoryRepository repo;public AdminCategoryController(CategoryRepository r){repo=r;}
 @GetMapping public List<Category> all(){return repo.findAll();}
 @PostMapping public Category create(@RequestBody Category c){if(c.getName()==null||c.getName().isBlank())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Category name required");return repo.save(c);}
 @PutMapping("/{id}") public Category update(@PathVariable Long id,@RequestBody Category c){Category old=repo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));old.setName(c.getName());old.setDescription(c.getDescription());return repo.save(old);}
 @DeleteMapping("/{id}") public Map<String,String> delete(@PathVariable Long id){if(!repo.existsById(id))throw new ResponseStatusException(HttpStatus.NOT_FOUND);repo.deleteById(id);return Map.of("message","Category deleted");}
}
