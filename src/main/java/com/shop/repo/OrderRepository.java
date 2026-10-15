package com.shop.repo;
import com.shop.model.ShopOrder; import java.util.List; import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderRepository extends JpaRepository<ShopOrder,Long>{List<ShopOrder> findByUserIdOrderByCreatedAtDesc(Long userId);}
