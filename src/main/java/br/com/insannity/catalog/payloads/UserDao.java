package br.com.insannity.catalog.payloads;

import br.com.insannity.catalog.entities.User;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDao {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<String> roles = new LinkedList<>();

    public UserDao(User entity) {
        this.id = entity.getId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        entity.getRoles().forEach(role -> this.roles.add(role.getAuthority()));
    }
}
