package com.gangadhar.e_commerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;

    private List<String> roles;
    // Optionally add more fields (like email, etc.)


}
