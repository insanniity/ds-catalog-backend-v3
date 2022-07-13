package br.com.insannity.catalog.payloads;

import br.com.insannity.catalog.entities.Category;
import br.com.insannity.catalog.entities.Product;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductDao {

    private Long id;
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @Positive(message = "Price must be positive")
    private Double price;
    private String imgUrl;
    @PastOrPresent(message = "Date must be in the past or present")
    private Instant date;
    private List<String> categories = new ArrayList<>();

    public ProductDao(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imgUrl = entity.getImgUrl();
        this.date = entity.getDate();
        entity.getCategories().forEach(category -> this.categories.add(category.getName()));
    }

}
