package com.shop.controller;
import com.shop.model.AppUser; import com.shop.repo.UserRepository;
import org.springframework.http.HttpStatus; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException;
import java.util.*;
@RestController @RequestMapping("/api")
public class UserController {
 private final UserRepository users; public UserController(UserRepository u){users=u;}
 @GetMapping("/users/me") public Map<String,Object> me(Authentication a){return safe(users.findByEmail(a.getName()).orElseThrow());}
 private Map<String,Object> safe(AppUser u){return Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail(),"role",u.getRole().name());}
 @GetMapping("/admin/users") public List<Map<String,Object>> all(){return users.findAll().stream().map(this::safe).toList();}
 @DeleteMapping("/admin/users/{id}") public Map<String,String> delete(@PathVariable Long id,Authentication a){
  AppUser target=users.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
  if(target.getEmail().equals(a.getName()))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You cannot delete your own admin account");
  users.delete(target);return Map.of("message","User deleted");
 }
}
