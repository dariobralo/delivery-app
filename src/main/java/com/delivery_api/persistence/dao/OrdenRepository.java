package com.delivery_api.persistence.dao;

import com.delivery_api.persistence.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
}
