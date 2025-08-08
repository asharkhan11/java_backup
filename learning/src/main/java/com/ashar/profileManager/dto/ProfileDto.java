package com.ashar.profileManager.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

    @NotBlank(message = "username cannot be null")
    @Size(min = 5,message = "username length must be greater than 4")
    private String username;
    @NotBlank(message = "password cannot be null")
    @Size(min = 7,message = "password length must be greater than 6")
    private String password;
    @NotNull(message = "profile url cannot be null")
    private String profilePicUrl;
    @NotNull(message = "bio cannot be null")
    private String bio;
    @NotNull(message = "user id cannot be null")
//    @Min(1)
    @Positive(message = "user id must be positive")
    private int userId;

}
