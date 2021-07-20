package com.example.cognitoexample;

import com.amazonaws.services.cognitoidp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CognitoService {

    private final CognitoClient cognitoClient;

    @Autowired
    public CognitoService(CognitoClient cognitoClient) {
        this.cognitoClient = cognitoClient;
    }

    public AdminCreateUserResult create(String userName, String password, String email, String phoneNumber) {
        return cognitoClient.createUser(userName, password, email, phoneNumber);
    }

    public AdminGetUserResult validate(String userName) {
        return cognitoClient.validateUser(userName);
    }

    public AdminInitiateAuthResult signIn(String userName, String password) {
        return cognitoClient.signIn(userName, password);
    }

    public SignUpResult signUp(String userName, String password, String email, String phoneNumber) {
        return cognitoClient.signUp(userName, password, email, phoneNumber);
    }

    public ConfirmSignUpResult verifyAccessCode(String userName, String code) {
        return cognitoClient.verifyAccessCode(userName, code);
    }
}
