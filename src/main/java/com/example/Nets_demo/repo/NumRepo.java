package com.example.Nets_demo.repo;

import com.example.Nets_demo.model.Nums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumRepo extends JpaRepository<Nums, Long> {
}
