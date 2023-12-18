package com.agroapp.repository;

import com.agroapp.models.Categoria;
import com.agroapp.models.Producto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query("SELECT p FROM Producto p WHERE p.categoria.categoriaId = :categoriaId")
    List<Producto> findByCategoriaId(@Param("categoriaId") int categoriaId);
}
