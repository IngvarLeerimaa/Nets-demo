package com.example.Nets_demo.DAO;

import com.example.Nets_demo.model.Nums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumDAO extends JpaRepository<Nums, Long> {}
