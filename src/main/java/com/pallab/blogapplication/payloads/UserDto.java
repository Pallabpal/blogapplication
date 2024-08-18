package com.pallab.blogapplication.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private int id;
    @NotEmpty
    @Size(min=4, message = "Username must be min of 4 characters")
    private String name;
    @Email(message = "Email address is not valid")
    private String email;
    @NotEmpty
    @Size(min=4, max=8, message = "Password must be greater than 4 characters and less than 8 characters")
    private  String password;
    @NotEmpty
    private  String about;
}
