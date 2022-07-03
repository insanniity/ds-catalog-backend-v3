package br.com.insannity.catalog.services;

import br.com.insannity.catalog.entities.Category;
import br.com.insannity.catalog.entities.Product;
import br.com.insannity.catalog.exceptions.DatabaseException;
import br.com.insannity.catalog.exceptions.EntityNotFoundException;
import br.com.insannity.catalog.payloads.CategoryDao;
import br.com.insannity.catalog.payloads.ProductDao;
import br.com.insannity.catalog.repositories.CategoryRepository;
import br.com.insannity.catalog.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDao> findAll(Pageable pageable) {
        Page<Product> list = repository.findAll(pageable);
        return list.map(prod ->  new ProductDao(prod, prod.getCategories()));
    }

    @Transactional(readOnly = true)
    public ProductDao findById(Long id) {
        Product enity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return new ProductDao(enity, enity.getCategories());
    }

    @Transactional
    public ProductDao insertNew(ProductDao dto) {
        Product enity = new Product();
        copyDtoEntity(dto, enity);
        enity = repository.save(enity);
        return new ProductDao(enity);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Id not found: "+id);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Product can't be deleted");
        }
    }

    public ProductDao update(Long id, ProductDao dto) {
        Product enity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id not found: "+id));
        copyDtoEntity(dto, enity);
        enity = repository.save(enity);
        return new ProductDao(enity);
    }


    private void copyDtoEntity (ProductDao dto, Product enity) {
        enity.setName(dto.getName());
        enity.setDescription(dto.getDescription());
        enity.setPrice(dto.getPrice());
        enity.setImgUrl(dto.getImgUrl());
        enity.setDate(dto.getDate());
        dto.getCategories().forEach(category -> enity.getCategories().add(categoryRepository.findByName(category)));
    }


}
