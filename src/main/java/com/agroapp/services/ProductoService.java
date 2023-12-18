package com.agroapp.services;

import com.agroapp.models.Producto;
import com.agroapp.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Obtener todos los productos
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    // Obtener un producto por ID
    public Optional<Producto> getProductoById(int productoId) {
        return productoRepository.findById(productoId);
    }

    // Guardar un nuevo producto o actualizar uno existente
    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Eliminar un producto por ID
    public void deleteProducto(int productoId) {
        productoRepository.deleteById(productoId);
    }

    // Guardar múltiples productos
    public List<Producto> saveProductos(List<Producto> productos) {
        return productoRepository.saveAll(productos);
    }

    // Obtener todos los productos de una categoría
    public List<Producto> getProductosByCategoria(int categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    // Actualizar un producto existente por ID
    public Producto updateProducto(int productoId, Producto producto) {
        Optional<Producto> existingProducto = getProductoById(productoId);

        if (existingProducto.isPresent()) {
            producto.setProductoId(productoId);
            return saveProducto(producto);
        } else {
            return null; // Puedes manejar el caso de que el producto no exista según tus necesidades
        }
    }
}
