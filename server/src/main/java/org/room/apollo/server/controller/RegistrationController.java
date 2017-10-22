package org.room.apollo.server.controller;

import org.room.apollo.server.dto.ExceptionResponse;
import org.room.apollo.server.dto.registration.RegistrationForm;
import org.room.apollo.server.dto.registration.RegistrationResponse;
import org.room.apollo.server.entity.User;
import org.room.apollo.server.exception.RegistrationException;
import org.room.apollo.server.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller with endpoints fro registration.
 */
@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * @param form Registration form.
     * @return Registred User details or Exception message.
     */
    @PostMapping
    public ResponseEntity<Object> registreNewUser(@Valid RegistrationForm form) {
        try {
            registrationService.isUsernameAndEmaillFree(form);
        } catch (RegistrationException e) {
            return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        User newUser = registrationService.createNewUser(form);
        return new ResponseEntity<>(new RegistrationResponse(newUser.getUsername(), newUser.getEmail(),
                "Successfully registered."),
                HttpStatus.OK);
    }
}
