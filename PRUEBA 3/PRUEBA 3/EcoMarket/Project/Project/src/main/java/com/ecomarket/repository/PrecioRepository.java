package com.ecomarket.repository;

import com.ecomarket.model.Precio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrecioRepository extends JpaRepository<Precio, Long> {
    List<Precio> findByProductoId(Long productoId);
}
