package com.example.restaurant_rating.repository;

import com.example.restaurant_rating.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
}