package com.example.cognitoexample;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CognitoClient {

    @Value("${CLIENTAPP_ID}")
    private String clientId;

    @Value("${POOL_ID}")
    private String userPoolId;

    private final AWSCognitoIdentityProvider cognitoIdentityProvider;

    public CognitoClient(@Value("${REGION}") String region, @Value("${ACCESS_KEY}") String accessKey, @Value("${SECRET_KEY}") String secretKey) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        this.cognitoIdentityProvider = AWSCognitoIdentityProviderClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withClientConfiguration(new ClientConfiguration())
                .withRegion(Regions.fromName(region))
                .build();
    }

    public AdminGetUserResult validateUser(String username) {

        AdminGetUserRequest getUserRequest = new AdminGetUserRequest();
        getUserRequest.setUsername(username);
        getUserRequest.setUserPoolId(userPoolId);


        return cognitoIdentityProvider.adminGetUser(getUserRequest);
    }


    public AdminCreateUserResult createUser(String username, String password, String email, String phonenumber) {

        AdminCreateUserRequest createUserRequest = new AdminCreateUserRequest();
        createUserRequest.setUsername(username);
        createUserRequest.setUserPoolId(userPoolId);
        createUserRequest.setTemporaryPassword(password);
        List<AttributeType> list = new ArrayList<>();

        AttributeType phoneNumberAttribute = new AttributeType();
        phoneNumberAttribute.setName("phone_number");
        phoneNumberAttribute.setValue(phonenumber);
        list.add(phoneNumberAttribute);

        AttributeType emailAttribute = new AttributeType();
        emailAttribute.setName("email");
        emailAttribute.setValue(email);
        list.add(emailAttribute);

        createUserRequest.setUserAttributes(list);


        return cognitoIdentityProvider.adminCreateUser(createUserRequest);
    }

    public AdminInitiateAuthResult signIn(String userName, String password) {

        final Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", userName);
        authParams.put("PASSWORD", password);

        final AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest();
        authRequest.withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                .withClientId(clientId)
                .withUserPoolId(userPoolId).
                withAuthParameters(authParams);

        return cognitoIdentityProvider.adminInitiateAuth(authRequest);
    }

    public SignUpResult signUp(String userName, String password, String email, String phoneNumber) {

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setClientId(clientId);
        signUpRequest.setUsername(userName);
        signUpRequest.setPassword(password);
        List<AttributeType> list = new ArrayList<>();

        AttributeType phoneNumberAttributeType = new AttributeType();
        phoneNumberAttributeType.setName("phone_number");
        phoneNumberAttributeType.setValue(phoneNumber);
        list.add(phoneNumberAttributeType);

        AttributeType emailAttribute = new AttributeType();
        emailAttribute.setName("email");
        emailAttribute.setValue(email);
        list.add(emailAttribute);

        signUpRequest.setUserAttributes(list);

        return cognitoIdentityProvider.signUp(signUpRequest);
    }


    public ConfirmSignUpResult verifyAccessCode(String username, String code) {

        ConfirmSignUpRequest confirmSignUpRequest = new ConfirmSignUpRequest();
        confirmSignUpRequest.setUsername(username);
        confirmSignUpRequest.setConfirmationCode(code);
        confirmSignUpRequest.setClientId(clientId);

        return cognitoIdentityProvider.confirmSignUp(confirmSignUpRequest);
    }
}
