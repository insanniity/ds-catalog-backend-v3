package br.com.insannity.catalog.entities;


import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString
@Entity
@Table(name = "tbl_category")
@SQLDelete(sql = "UPDATE tbl_category SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class Category extends AbstractEntity {

    private String name;

    @ManyToMany(mappedBy = "categories")
    @Setter(AccessLevel.PROTECTED)
    private Set<Product> products = new LinkedHashSet<>();

}
