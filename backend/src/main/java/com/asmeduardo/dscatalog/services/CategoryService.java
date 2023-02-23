package com.asmeduardo.dscatalog.services;

import com.asmeduardo.dscatalog.dto.CategoryDTO;
import com.asmeduardo.dscatalog.models.Category;
import com.asmeduardo.dscatalog.repositories.CategoryRepository;
import com.asmeduardo.dscatalog.services.exceptions.DatabaseException;
import com.asmeduardo.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> getAllCategoriesPaged(Pageable pageable) {
        Page<Category> result = categoryRepository.findAll(pageable);
        return result.map(CategoryDTO::new);
    }

    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO insertCategory(CategoryDTO dto) {
        Category entity = new Category();
        copyDtoToEntity(dto, entity);
        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
        try {
            Category entity = categoryRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = categoryRepository.save(entity);
            return new CategoryDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteCategory(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void copyDtoToEntity(CategoryDTO dto, Category entity) {
        entity.setName(dto.getName());
    }
}
