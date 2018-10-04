package com.fms.friendsmanagementsystem.apitest;

import com.fms.friendsmanagementsystem.FriendsManagementSystemApplication;
import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FriendsManagementSystemApplication.class)
@SpringBootTest
public class FMSApiTest {

    @Before
    public void setUp() throws Exception {
        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = Integer.valueOf(8080);
        }
        else{
            RestAssured.port = Integer.valueOf(port);
        }


        String basePath = System.getProperty("server.base");
        if(basePath==null){
            basePath = "/";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if(baseHost==null){
            //baseHost = "http://localhost";
            baseHost = "http://52.74.189.137";
        }
        RestAssured.baseURI = baseHost;

        //System.out.println("RestAssured.baseURI: "+RestAssured.baseURI);
        //System.out.println("RestAssured.port: "+RestAssured.port);
        //System.out.println("RestAssured.basePath: "+RestAssured.basePath);
    }

    @Test
    public void createFriendApiTest() throws Exception{
        given()
            .content("application/json")
            .body("{\"friends\":[\"swapnil@example.com\",\"reyom@example.com\"]}")
            .when().post("/create-friend").then()
            .statusCode(200)
        ;

        given()
            .content("application/json")
            .body("{\"friends\":[\"supreet@example.com\",\"reyom@example.com\"]}")
            .when().post("/create-friend").then()
            .statusCode(200);

        given()
            .content("application/json")
            .body("{\"friends\":[\"swapnil@example.com\",\"reyom@example.com\"]}")
            .when().post("/create-friend").then()
            .statusCode(200)
            .body("success", equalTo(false))
            .body("errorCode", equalTo("1001"))
            .body("errorMessage", equalTo("Already a Friend"));

        System.out.println("createFriendApiTest Successful!");
    }

    @Test
    public void retrieveFriendApiTest() throws Exception{
        given()
            .content("application/json")
            .body("{\"email\" : \"swapnil@example.com\"}")
            .when().post("/retrieve-friend").then()
            .statusCode(200)
            .body("success", equalTo(true))
            .body("friends", hasSize(1))
            .body("count", equalTo(1));

        given()
            .content("application/json")
            .body("{\"email\" : \"swapnil@gmail.com\"}")
            .when().post("/retrieve-friend").then()
            .statusCode(200)
            .body("success", equalTo(true))
            .body("friends", hasSize(0))
            .body("count", equalTo(0));

        System.out.println("retrieveFriendApiTest Successful!");
    }

    @Test
    public void retrieveCommonFriendApiTest() throws Exception{
        given()
            .content("application/json")
            .body("{\n"
                + "  \"friends\" : [\n"
                + "  \t\"supreet@example.com\",\n"
                + "    \"swapnil@example.com\"\n"
                + "  ]\n"
                + "}")
            .when().post("/retrieve-common-friend").then()
            .statusCode(200)
            .body("success", equalTo(true))
            .body("friends", hasSize(1))
            .body("friends", equalTo(Arrays.asList("reyom@example.com")))
            .body("count", equalTo(1));

        System.out.println("retrieveCommonFriendApiTest Successful!");
    }

    @Test
    public void subscribeFriendApiTest() throws Exception{
        given()
            .content("application/json")
            .body("{\n"
                + "  \"requestor\": \"swapnil@example.com\",\n"
                + "  \"target\": \"neha@example.com\"\n"
                + "}")
            .when().post("/subscribe-friend").then()
            .statusCode(200)
            .body("success", equalTo(true));

        System.out.println("subscribeFriendApiTest Successful!");
    }

    @Test
    public void blockFriendApiTest() throws Exception{
        given()
            .content("application/json")
            .body("{\n"
                + "  \"requestor\": \"swapnil@example.com\",\n"
                + "  \"target\": \"neha@example.com\"\n"
                + "}")
            .when().post("/block-friend").then()
            .statusCode(200)
            .body("success", equalTo(true));

        System.out.println("blockFriendApiTest Successful!");
    }

    @Test
    public void addBlockedFriendApiTest() throws Exception{
        given()
            .content("application/json")
            .body("{\n"
                + "  \"friends\":\n"
                + "    [\n"
                + "      \"swapnil@example.com\",\n"
                + "      \"neha@example.com\"\n"
                + "    ]\n"
                + "}")
            .when().post("/create-friend").then()
            .statusCode(200)
            .body("success", equalTo(false))
            .body("errorMessage", equalTo("Friend is blocked"));

        System.out.println("addBlockedFriendApiTest Successful!");
    }

    @Test
    public void retrieveUpdateFriendApiTest() throws Exception{
        given()
            .content("application/json")
            .body("{\n"
                + "  \"sender\":  \"swapnil@example.com\",\n"
                + "  \"text\": \"Hello World! kate@example.com,swapnil.suhane@gmail.com\"\n"
                + "}")
            .when().post("/retrieve-update-friend").then()
            .statusCode(200)
            .body("success", equalTo(true))
            .body("recipients", hasSize(greaterThan(0)))
            .body("recipients", hasItem("kate@example.com"));

        System.out.println("retrieveUpdateFriendApiTest Successful!");
    }

    //sample test
    //@Test
    //public void makeSureThatGoogleIsUp() {
    //    given().when().get("http://www.google.com").then().statusCode(200);
    //}
}
