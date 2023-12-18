package com.agroapp.services;

import com.agroapp.models.Categoria;
import com.agroapp.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria saveCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void deleteCategoria(int categoriaId) {
        categoriaRepository.deleteById(categoriaId);
    }

    // Método para guardar múltiples categorías
    public List<Categoria> saveCategorias(List<Categoria> categorias) {
        return categoriaRepository.saveAll(categorias);
    }

    // Método para obtener una categoría por ID
    public Optional<Categoria> getCategoriaById(int categoriaId) {
        return categoriaRepository.findById(categoriaId);
    }

    public Categoria updateCategoria(int categoriaId, Categoria categoria) {
        // Implementa la lógica para actualizar una categoría existente
        // Puedes adaptar esto según tus necesidades específicas
        return null;
    }
}
