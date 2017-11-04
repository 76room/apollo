package org.room.apollo.server.controller;

import org.room.apollo.server.dto.ExceptionResponse;
import org.room.apollo.server.dto.deezer.DeezerToken;
import org.room.apollo.server.dto.login.RegistrationForm;
import org.room.apollo.server.dto.login.SigninForm;
import org.room.apollo.server.dto.login.SigninResponse;
import org.room.apollo.server.service.AuthenticationService;
import org.room.apollo.server.service.AuthorizationService;
import org.room.apollo.server.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Controller for all request to /signin url. Provide authorization api.
 */
@RestController
@RequestMapping("/signin")
public class AuthorizationController {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    private final AuthorizationService authorizationService;

    private final AuthenticationService authenticationService;

    private final RegistrationService registrationService;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService,
                                   AuthenticationService authenticationService,
                                   RegistrationService registrationService) {
        this.authorizationService = authorizationService;
        this.authenticationService = authenticationService;
        this.registrationService = registrationService;
    }

    @GetMapping
    public ResponseEntity<Object> signin(@RequestParam(required = false) String logout) {
        if (logout != null) {
            return new ResponseEntity<>("Successfully logout.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Requires sign in.", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/failed")
    public ResponseEntity<Object> failedSignin() {
        return new ResponseEntity<>(new ExceptionResponse("Credentials wrong or not present."), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/success")
    public ResponseEntity<Object> successSignin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LOG.info(auth.toString());
        return new ResponseEntity<>(new SigninResponse(auth, "Successfully sign in user."), HttpStatus.OK);
    }

    /**
     * Enter point fro authorization with deezer.
     * In first step we redirect user to deezer.
     *
     * @return response entity that redirect to deezer api for authorization.
     */
    @GetMapping("/deezer/step1")
    public ResponseEntity<Object> signinWithDeezer() {
        LOG.info("New request for login through Deezer.");
        return authorizationService.getRedirectResponseForDeezerAuthorization();
    }

    /**
     * In second step we get token for deezer, put it in the httpSession and redirect to step 3.
     * In case of error we send json with error and Http status 400(Bad request).
     *
     * @param code         a code generated by deezer api for requesting accessing token.
     * @param error        deezer error.
     * @param redirect_uri deezer redirect uri.
     * @return redirect to step3.
     */
    @GetMapping("/deezer/step2")
    public ResponseEntity<Object> userAcceptAuthorizationWithDeezer(
            HttpSession session,
            HttpServletResponse response,
            @RequestParam(name = "code", required = false) String code,
            @RequestParam(name = "error_reason", required = false) String error,
            @RequestParam(name = "redirect_uri", required = false) String redirect_uri) throws IOException {
        LOG.info("New response from deezer code: {} , error_reason: {} , redirect_uri: {} .",
                code, error, redirect_uri);
        if (error != null) {
            LOG.warn("Deezer respond with error: {}", error);
            return new ResponseEntity<>(new ExceptionResponse(error), HttpStatus.FORBIDDEN);
        }
        DeezerToken token = authorizationService.getDeezerAccessToken(code);
        session.setAttribute("deezerToken", token);
        response.sendRedirect("/signin/deezer/step3");
        LOG.debug("Redirecting to step3 with token {}", token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Gating data from deezer api, form from them RegistrationForm if username is invalid generate new one.
     * Next register and signin user.
     *
     * @param session http session.
     * @return @see(SigninResponse) if successfully signin deezer user. Exception response if cant signin user.
     */
    @GetMapping("/deezer/step3")
    public ResponseEntity<Object> getDeezerUserData(HttpSession session)
            throws IOException {
        DeezerToken token = (DeezerToken) session.getAttribute("deezerToken");
        LOG.info("On deezer step 3, token: {} from session.", token);
        if (token == null) {
            LOG.error("Singing for Deezer step 3 failed to retrive token from session. Session was: {}", session);
            return new ResponseEntity<>(new ExceptionResponse("Cant register user."), HttpStatus.BAD_REQUEST);
        }
        RegistrationForm userData = authorizationService.getUserDataFromDeezerApi(token);
        LOG.info("User data from Deezer API: {}", userData);
        return processUserFromExternalAPI(userData);
    }

    /**
     * Register and signin user that come from external API.
     */
    private ResponseEntity<Object> processUserFromExternalAPI(RegistrationForm userData) {
        authorizationService.normalizeUsername(userData);
        registrationService.registerUserFromExternalAPI(userData);
        SigninForm signinData = new SigninForm(userData);
        return signinUserFromExternalAPI(signinData);
    }

    private ResponseEntity<Object> signinUserFromExternalAPI(SigninForm signinData) {
        try {
            SigninResponse response = authenticationService.signinUserFromExternalAPI(signinData);
            LOG.info("Sign in user: {} from external API. Response: {}", signinData, response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AuthenticationException | IllegalArgumentException e) {
            LOG.error("Cant sign in user from external API. SigninForm: {} exception: {}", signinData, e);
            return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
