package com.delivery_api.persistence.dao;

import com.delivery_api.persistence.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComidaRepository extends JpaRepository<Producto, Long> {
}
