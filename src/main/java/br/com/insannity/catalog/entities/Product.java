package br.com.insannity.catalog.entities;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString
@Entity
@Table(name = "tbl_product")
@SQLDelete(sql = "UPDATE tbl_product SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class Product extends AbstractEntity{

    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Double price;
    @URL
    private String imgUrl;
    private Instant date;

    @ManyToMany
    @Setter(AccessLevel.PROTECTED)
    @JoinTable(name = "tbl_product_category",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();


}
