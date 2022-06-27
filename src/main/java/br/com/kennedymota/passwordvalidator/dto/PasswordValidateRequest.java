package br.com.kennedymota.passwordvalidator.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class PasswordValidateRequest {

    @NotNull(
            message = "Property password is required"
    )
    private String password;

}
