package br.com.insannity.catalog.services;

import br.com.insannity.catalog.entities.Category;
import br.com.insannity.catalog.exceptions.DatabaseException;
import br.com.insannity.catalog.exceptions.EntityNotFoundException;
import br.com.insannity.catalog.payloads.CategoryDao;
import br.com.insannity.catalog.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDao> findAll(Pageable pageable) {
        Page<Category> list = repository.findAll(pageable);
        return list.map(CategoryDao::new);
    }

    @Transactional(readOnly = true)
    public CategoryDao findById(Long id) {
        Category enity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return new CategoryDao(enity);
    }

    @Transactional
    public CategoryDao insertNew(CategoryDao categoryDao) {
        Category enity = new Category();
        copyDtoEntity(categoryDao, enity);
        enity = repository.save(enity);
        return new CategoryDao(enity);
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

    public CategoryDao update(Long id, CategoryDao dto) {
        Category enity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id not found: "+id));
        copyDtoEntity(dto, enity);
        enity = repository.save(enity);
        return new CategoryDao(enity);
    }

    private void copyDtoEntity (CategoryDao categoryDao, Category category) {
        category.setName(categoryDao.getName());
    }

}
