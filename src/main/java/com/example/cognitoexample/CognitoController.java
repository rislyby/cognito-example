package com.example.cognitoexample;

import com.amazonaws.services.cognitoidp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CognitoController {

    private final CognitoService cognitoService;

    @Autowired
    public CognitoController(CognitoService cognitoService) {
        this.cognitoService = cognitoService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public AdminCreateUserResult adminCreate(@RequestParam String userName, @RequestParam String password, @RequestParam String email, @RequestParam String phoneNumber) {
        return cognitoService.create(userName, password, email, phoneNumber);
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    public AdminGetUserResult login(@RequestParam String userName) {
        return cognitoService.validate(userName);
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public AdminInitiateAuthResult adminCreate(@RequestParam String userName, @RequestParam String password) {
        return cognitoService.signIn(userName, password);
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public SignUpResult signUp(@RequestParam String userName, @RequestParam String password, @RequestParam String email, @RequestParam String phoneNumber) {
        return cognitoService.signUp(userName, password, email, phoneNumber);
    }

    @RequestMapping(value = "/signUp/verify", method = RequestMethod.POST)
    public ConfirmSignUpResult signUp(@RequestParam String userName, @RequestParam String code) {
        return cognitoService.verifyAccessCode(userName, code);
    }


}
