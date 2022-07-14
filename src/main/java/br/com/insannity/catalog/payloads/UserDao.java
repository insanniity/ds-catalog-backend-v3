package br.com.insannity.catalog.payloads;

import br.com.insannity.catalog.entities.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
    @NotBlank(message = "Name is required")
    private String firstName;
    @NotBlank(message = "Name is required")
    private String lastName;
    @Email(message = "Email not valid")
    private String email;

    private Boolean enabled;

    private List<String> roles = new LinkedList<>();

    public UserDao(User entity) {
        this.id = entity.getId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.email = entity.getEmail();
        entity.getRoles().forEach(role -> this.roles.add(role.getAuthority()));
    }
}
