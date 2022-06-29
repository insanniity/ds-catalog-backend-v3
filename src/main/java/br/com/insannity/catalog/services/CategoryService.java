package br.com.insannity.catalog.services;

import br.com.insannity.catalog.entities.Category;
import br.com.insannity.catalog.exceptions.DatabaseException;
import br.com.insannity.catalog.exceptions.EntityNotFoundException;
import br.com.insannity.catalog.payloads.CategoryDao;
import br.com.insannity.catalog.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDao> findAll() {
        List<Category> list = repository.findAll();
        return list.stream().map(CategoryDao::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDao findById(Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return new CategoryDao(category);
    }

    @Transactional
    public CategoryDao insertNew(CategoryDao categoryDao) {
        Category category = new Category();
        copyDtoEntity(categoryDao, category);
        category = repository.save(category);
        return new CategoryDao(category);
    }


    private void copyDtoEntity (CategoryDao categoryDao, Category category) {
        category.setName(categoryDao.getName());
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Id not found: "+id);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Category can't be deleted");
        }
    }

    public CategoryDao update(Long id, CategoryDao categoryDao) {
        Category category = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id not found: "+id));
        copyDtoEntity(categoryDao, category);
        category = repository.save(category);
        return new CategoryDao(category);
    }


}
