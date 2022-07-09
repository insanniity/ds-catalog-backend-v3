package br.com.insannity.catalog.payloads;

import br.com.insannity.catalog.entities.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RoleDao {

    private Long id;
    private String authority;
    private String description;

    public RoleDao(Role entity) {
        this.id = entity.getId();
        this.authority = entity.getAuthority();
        this.description = entity.getDescription();
    }
}
