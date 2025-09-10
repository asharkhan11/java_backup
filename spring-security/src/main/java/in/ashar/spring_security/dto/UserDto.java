package in.ashar.spring_security.dto;

import in.ashar.spring_security.utility.Roles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "username must not be null")
    private String username;

    @NotBlank(message = "password must not be null")
    @Length(min = 5, message = "length of password must be greater than 5")
    private String password;

    @NotEmpty(message = "at least one role must be provided")
    private Set<Roles> roles;

}
