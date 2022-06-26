package br.com.insannity.catalog.repositories;

import br.com.insannity.catalog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}