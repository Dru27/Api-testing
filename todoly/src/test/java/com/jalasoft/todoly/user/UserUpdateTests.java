package com.jalasoft.todoly.user;

import api.APIManager;
import api.methods.APIUserMethods;
import constants.Constants;
import entities.user.User;
import framework.Environment;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.LoggerManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserUpdateTests {
    private static final Environment environment = Environment.getInstance();
    private static final APIManager apiManager = APIManager.getInstance();
    private static final LoggerManager log = LoggerManager.getInstance();
    private final ArrayList<User> users = new ArrayList<>();
    Map<String, Object> jsonAsMap = new HashMap<>();

    @BeforeClass
    public void setup() {
        users.add(APIUserMethods.createUser(Constants.EMAIL1_EC, Constants.USERNAME_EC, Constants.PASSWORD_EC));
    }

    @Test
    public void updateUserEmail() {
        log.info("Verify that is possible update information of a user based on input data when doing a UPDATE request to \"https://todo.ly/api/user/0.json\"");
        apiManager.setCredentials(Constants.EMAIL1_EC,Constants.PASSWORD_EC);
        jsonAsMap.put("Email", "test1@123.com");

        Response response = apiManager.put(environment.getUserUpdateEndpoint(), ContentType.JSON, jsonAsMap );
        User responseUser = response.as(User.class);

        Assert.assertEquals(response.getStatusCode(), 200, "Correct status code is not returned");
        Assert.assertTrue(response.getStatusLine().contains("200 OK"), "Correct status code and message is not returned");
        Assert.assertNull(response.jsonPath().getString("ErrorMessage"), "Error Message was returned");
        Assert.assertNull(response.jsonPath().getString("ErrorCode"), "Error Code was returned");
        Assert.assertEquals(responseUser.getEmail(), jsonAsMap.get("Email"), "Incorrect Email value was set");
    }

    @AfterClass
    public void teardown() {
        boolean isUserDeleted = APIUserMethods.deleteUser(jsonAsMap.get("Email").toString(),Constants.PASSWORD_EC);
        Assert.assertTrue(isUserDeleted, "User was not deleted");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        try {
            if(result.getStatus() == ITestResult.SUCCESS) {
                log.warn("Test 'PASSED'");
            }
            else if(result.getStatus() == ITestResult.FAILURE) {
                log.warn("Test 'FAILED'");
            }
            else if(result.getStatus() == ITestResult.SKIP ) {
                log.warn("Test 'BLOCKED'");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
