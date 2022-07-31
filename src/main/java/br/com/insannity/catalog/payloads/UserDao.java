package br.com.insannity.catalog.payloads;

import java.util.LinkedList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import br.com.insannity.catalog.entities.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
        this.enabled = entity.isEnabled();
        entity.getRoles().forEach(role -> this.roles.add(role.getAuthority()));
    }
}
