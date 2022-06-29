package br.com.insannity.catalog.payloads;

import br.com.insannity.catalog.entities.Category;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CategoryDao {

    private Long id;
    private String name;

    public CategoryDao(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
