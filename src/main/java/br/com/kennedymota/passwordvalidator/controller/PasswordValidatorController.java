package br.com.kennedymota.passwordvalidator.controller;

import br.com.kennedymota.passwordvalidator.dto.PasswordValidateRequest;
import br.com.kennedymota.passwordvalidator.dto.ResponseHttp;
import br.com.kennedymota.passwordvalidator.service.interfaces.PasswordValidatorService;
import br.com.kennedymota.passwordvalidator.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping(value = "/api/password")
public class PasswordValidatorController {

    @Autowired
    private PasswordValidatorService passwordValidatorService;

    @RequestMapping(method = RequestMethod.POST, path = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity validate (@Valid @RequestBody final PasswordValidateRequest request) {
        ResponseHttp response = new ResponseHttp();
        try {
            Boolean pass = passwordValidatorService.checkPassword(request.getPassword());
            response.setData(pass);

            if (pass) {
                response.setMessage("Password OK");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(response);
            } else {
                response.setMessage("Bad Password");
                response.setErrors(Collections.singletonList(Errors.BAD_PASSWORD.getMessage()));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(response);
            }
        } catch (Exception ex) {
            response.setData(null);
            response.setMessage(Errors.GENERIC_ERROR.getMessage());
            response.setErrors(Collections.singletonList(ex.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }


}
