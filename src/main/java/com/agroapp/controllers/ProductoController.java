package com.agroapp.controllers;

import com.agroapp.models.Producto;
import com.agroapp.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Obtener todos los productos
    @GetMapping("/lista-productos")
    public List<Producto> getAllProductos() {
        return productoService.getAllProductos();
    }

    // Obtener un producto por ID
    @GetMapping("/producto/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable("id") int productoId) {
        return productoService.getProductoById(productoId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear varios productos
    @PostMapping("/crear-productos")
    public ResponseEntity<List<Producto>> createProductos(@RequestBody List<Producto> productos) {
        List<Producto> createdProductos = productoService.saveProductos(productos);
        return ResponseEntity.ok(createdProductos);
    }

    // Crear un nuevo producto
    @PostMapping("/producto")
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        Producto createdProducto = productoService.saveProducto(producto);
        return ResponseEntity.ok(createdProducto);
    }

    // Actualizar un producto existente por ID
    @PutMapping("/producto/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable("id") int productoId, @RequestBody Producto producto) {
        Producto updatedProducto = productoService.updateProducto(productoId, producto);

        if (updatedProducto != null) {
            return ResponseEntity.ok(updatedProducto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un producto por ID
    @DeleteMapping("/producto/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable("id") int productoId) {
        if (productoService.getProductoById(productoId).isPresent()) {
            productoService.deleteProducto(productoId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener todos los productos de una categoría
    @GetMapping("/productos-por-categoria/{categoriaId}")
    public ResponseEntity<List<Producto>> getProductosByCategoria(@PathVariable("categoriaId") int categoriaId) {
        List<Producto> productos = productoService.getProductosByCategoria(categoriaId);
        return ResponseEntity.ok(productos);
    }
}
