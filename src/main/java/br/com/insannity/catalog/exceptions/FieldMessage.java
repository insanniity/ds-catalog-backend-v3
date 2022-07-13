package br.com.insannity.catalog.exceptions;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FieldMessage {

    private String fieldName;
    private String message;

}
