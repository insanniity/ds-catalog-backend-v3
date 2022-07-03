package br.com.insannity.catalog.repositories;

import br.com.insannity.catalog.entities.Category;
import br.com.insannity.catalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}