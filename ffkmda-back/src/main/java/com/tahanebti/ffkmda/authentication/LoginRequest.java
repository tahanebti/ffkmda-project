package com.tahanebti.ffkmda.authentication;

import javax.validation.constraints.NotBlank;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequest {

    @Schema(example = "admin")
    @NotBlank
    private String username;

    @Schema(example = "admin")
    @NotBlank
    private String password;
}
