package com.shop.repo;
import com.shop.model.Category; import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoryRepository extends JpaRepository<Category,Long>{}
