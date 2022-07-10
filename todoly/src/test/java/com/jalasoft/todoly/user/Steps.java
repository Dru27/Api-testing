package com.jalasoft.todoly.user;

import entities.user.User;
import framework.Environment;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.response.Response;

public class Steps {

    private static final Environment environment = Environment.getInstance();
    private static Response response;

    public static void baseUri() {
        RestAssured.baseURI = environment.getBaseURL();
    }

    public static void basePath() {
        RestAssured.basePath = environment.getBasePath();
    }

    public static void credentials() {
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName(environment.getUserName());
        authScheme.setPassword(environment.getPassword());
        RestAssured.authentication = authScheme;
    }

    public static void request() {
        String userEndpoint = environment.getUserEndpoint();
        response = RestAssured.given().get(userEndpoint);
    }

    public static User retreaveUser(){
        User responseUser = response.as(User.class);
        return responseUser;
    }
}
