package com.ashar.profileManager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "name cannot be null")
    private String name;
    @NotNull(message = "date of birth cannot be null")
    @Past(message = "date of birth must be less than present date")
    private Date dateOfBirth;
    @NotEmpty()//message = "address cannot be null"
    private String address;
}
