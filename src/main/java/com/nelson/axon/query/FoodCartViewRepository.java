package com.nelson.axon.query;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodCartViewRepository extends JpaRepository<FoodCartView, UUID> {
    
}
