package com.agroapp.controllers;

import com.agroapp.models.Categoria;
import com.agroapp.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/lista-categorias")
    public List<Categoria> getAllCategorias() {
        return categoriaService.getAllCategorias();
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable("id") int categoriaId) {
        return categoriaService.getCategoriaById(categoriaId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/crear-categorias")
    public ResponseEntity<List<Categoria>> createCategorias(@RequestBody List<Categoria> categorias) {
        List<Categoria> createdCategorias = categoriaService.saveCategorias(categorias);
        return ResponseEntity.ok(createdCategorias);
    }

    @PutMapping("/categoria/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable("id") int categoriaId, @RequestBody Categoria categoria) {
        if (categoriaService.getCategoriaById(categoriaId).isPresent()) {
            categoria.setCategoriaId(categoriaId);
            Categoria updatedCategoria = categoriaService.saveCategoria(categoria);
            return ResponseEntity.ok(updatedCategoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/categoria/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable("id") int categoriaId) {
        if (categoriaService.getCategoriaById(categoriaId).isPresent()) {
            categoriaService.deleteCategoria(categoriaId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
