package com.jalasoft.todoly.user;

import entities.user.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Reader;

public class KDTest {
    @Test
    public void getUserInfo() throws Exception {
        String path = "src/test/resources/excel/users/test.xlsx";
        Reader.setExcelFile(path, "Hoja1");

        User user1 = null;
        for (int i = 1; i <= 5; i++){
            String Action = Reader.getCellData(i, 1);
            if (Action.equals("baseURI")) {
                Steps.baseUri();
            } else if (Action.equals("basePath")) {
                Steps.basePath();
            } else if (Action.equals("endpoint")) {
                Steps.credentials();
            } else if (Action.equals("request")) {
                Steps.request();
            } else if (Action.equals("retrieveUserInfo")) {
                user1 = Steps.retreaveUser();
            }

        }
        Assert.assertEquals(user1.getId(), 704600, "Id value is incorrect");
    }
}
