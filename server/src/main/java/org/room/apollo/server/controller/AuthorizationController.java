package org.room.apollo.server.controller;

import org.room.apollo.server.dto.ExceptionResponse;
import org.room.apollo.server.dto.deezer.DeezerToken;
import org.room.apollo.server.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/**
 * Controller for all request to /signin url. Provide authorization api.
 */
@RestController
@RequestMapping("/signin")
public class AuthorizationController {

    private AuthorizationService authorizationService;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public ResponseEntity<String> signin() {
        //TODO get for /singin
        return new ResponseEntity<>("placeholder", HttpStatus.OK);
    }

    /**
     * Enter point fro authorization with deezer.
     * In first step we redirect user ti deezer.
     *
     * @return response entity that redirect to deezer api for authorization.
     */
    @GetMapping("/deezer/step1")
    public ResponseEntity<Object> signinWithDeezer() {
        return authorizationService.getRedirectResponseForDeezerAuthorization();
    }

    /**
     * In second step we get token for deezer, put it in the httpSession and return it to frontend.
     * In case of error we send json with error and Http status 400(Bad request).
     *
     * @param code         a code generated by deezer api for requesting accessing token.
     * @param error        deezer error.
     * @param redirect_uri deezer redirect uri.
     * @return Deezer token if successfully authorize user via deezer api.
     */
    @GetMapping("/deezer/step2")
    public ResponseEntity<Object> userAcceptAuthorizationWithDeezer(
            HttpSession session,
            @RequestParam(name = "code", required = false) String code,
            @RequestParam(name = "error_reason", required = false) String error,
            @RequestParam(name = "redirect_uri", required = false) String redirect_uri) {
        if (error != null) {
            return new ResponseEntity<>(new ExceptionResponse(error), HttpStatus.BAD_REQUEST);
        }
        try {
            DeezerToken token = authorizationService.getDeezerAccessToken(code);
            session.setAttribute("deezerToken", token);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
