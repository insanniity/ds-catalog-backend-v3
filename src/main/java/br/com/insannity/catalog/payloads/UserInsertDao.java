package br.com.insannity.catalog.payloads;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class UserInsertDao extends UserDao {

    private String password;

}
