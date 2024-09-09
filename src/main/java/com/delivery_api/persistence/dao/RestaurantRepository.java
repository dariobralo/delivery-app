package com.delivery_api.persistence.dao;

import com.delivery_api.persistence.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
