package br.com.insannity.catalog.services;

import br.com.insannity.catalog.entities.Category;
import br.com.insannity.catalog.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository repository;


    public List<Category> findAll() {
        return repository.findAll();
    }


}
