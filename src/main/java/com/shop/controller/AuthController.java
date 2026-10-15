package com.shop.controller;
import com.shop.model.*; import com.shop.repo.UserRepository; import com.shop.security.JwtService;
import jakarta.validation.Valid; import jakarta.validation.constraints.*;
import org.springframework.http.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UserRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
 public AuthController(UserRepository u,PasswordEncoder e,JwtService j){users=u;encoder=e;jwt=j;}
 public record RegisterRequest(@NotBlank String name,@Email @NotBlank String email,@Size(min=8) String password){}
 public record LoginRequest(@Email @NotBlank String email,@NotBlank String password){}
 @PostMapping("/register") public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest r){
  if(users.existsByEmail(r.email().toLowerCase()))return ResponseEntity.status(409).body(Map.of("message","Email is already registered"));
  AppUser u=users.save(new AppUser(r.name().trim(),r.email().toLowerCase(),encoder.encode(r.password()),Role.USER));return ResponseEntity.status(201).body(payload(u));
 }
 @PostMapping("/login") public ResponseEntity<?> login(@Valid @RequestBody LoginRequest r){
  return users.findByEmail(r.email().toLowerCase()).filter(u->encoder.matches(r.password(),u.getPassword())).<ResponseEntity<?>>map(u->ResponseEntity.ok(payload(u))).orElseGet(()->ResponseEntity.status(401).body(Map.of("message","Invalid email or password")));
 }
 private Map<String,Object> payload(AppUser u){return Map.of("token",jwt.generate(u.getEmail(),u.getRole().name()),"user",Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail(),"role",u.getRole().name()));}
}
