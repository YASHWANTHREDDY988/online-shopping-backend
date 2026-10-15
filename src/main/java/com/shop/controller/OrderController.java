package com.shop.controller;
import com.shop.model.*; import com.shop.repo.*;
import org.springframework.http.HttpStatus; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal; import java.util.*;
@RestController @RequestMapping("/api")
public class OrderController {
 private final OrderRepository orders; private final UserRepository users; private final ProductRepository products;
 public OrderController(OrderRepository o,UserRepository u,ProductRepository p){orders=o;users=u;products=p;}
 public record ItemRequest(Long productId,int quantity){}
 public record CheckoutRequest(List<ItemRequest> items,String shippingAddress,String paymentMethod){}
 @PostMapping("/orders") public ShopOrder checkout(@RequestBody CheckoutRequest r,Authentication auth){
  if(r.items()==null||r.items().isEmpty())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cart is empty");
  if(r.shippingAddress()==null||r.shippingAddress().isBlank())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Shipping address is required");
  AppUser u=users.findByEmail(auth.getName()).orElseThrow();BigDecimal total=BigDecimal.ZERO;List<String> lines=new ArrayList<>();
  for(ItemRequest i:r.items()){
   if(i.quantity()<1||i.quantity()>99)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid quantity");
   Product p=products.findById(i.productId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));
   if(p.getStock()<i.quantity())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Insufficient stock for "+p.getName());
   p.setStock(p.getStock()-i.quantity());products.save(p);total=total.add(p.getPrice().multiply(BigDecimal.valueOf(i.quantity())));
   lines.add(p.getName()+" x "+i.quantity()+" (₹"+p.getPrice()+")");
  }
  return orders.save(new ShopOrder(u.getId(),u.getName(),u.getEmail(),String.join(", ",lines),r.shippingAddress(),total));
 }
 @GetMapping("/orders") public List<ShopOrder> myOrders(Authentication a){AppUser u=users.findByEmail(a.getName()).orElseThrow();return orders.findByUserIdOrderByCreatedAtDesc(u.getId());}
 @DeleteMapping("/orders/{id}") public Map<String,String> cancel(@PathVariable Long id,Authentication a){
  AppUser u=users.findByEmail(a.getName()).orElseThrow();ShopOrder o=orders.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
  if(!o.getUserId().equals(u.getId()))throw new ResponseStatusException(HttpStatus.FORBIDDEN);
  if(!"CONFIRMED".equals(o.getStatus()))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Only confirmed orders can be cancelled");
  o.setStatus("CANCELLED");orders.save(o);return Map.of("message","Order cancelled");
 }
 @GetMapping("/admin/orders") public List<ShopOrder> all(){return orders.findAll().stream().sorted(Comparator.comparing(ShopOrder::getCreatedAt).reversed()).toList();}
 @PatchMapping("/admin/orders/{id}/status") public ShopOrder status(@PathVariable Long id,@RequestBody Map<String,String> body){
  ShopOrder o=orders.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));String s=body.getOrDefault("status","");
  if(!Set.of("CONFIRMED","PROCESSING","SHIPPED","DELIVERED","CANCELLED").contains(s))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid order status");
  o.setStatus(s);return orders.save(o);
 }
 @GetMapping("/admin/reports") public Map<String,Object> report(){
  List<ShopOrder> all=orders.findAll();BigDecimal sales=all.stream().filter(o->!"CANCELLED".equals(o.getStatus())).map(ShopOrder::getTotal).reduce(BigDecimal.ZERO,BigDecimal::add);
  return Map.of("orders",all.size(),"sales",sales,"users",users.count(),"products",products.count());
 }
}
