package com.themoviedb.api.tests.services;

import com.jayway.restassured.response.Response;
import com.themoviedb.api.components.config.EndPoint;
import com.themoviedb.api.tests.BaseAPITMDTest;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class AuthenticationIT extends BaseAPITMDTest{

    @Test
    public void testCreateGuestSession() {
        Response createTokenResponse =
                given().
                        spec(tmd_requestSpec).
                        when().get(EndPoint.AUTH_CREATE_TOKEN+"api_key="+api_key).
                        then().log().all().
                        extract().response();

        assertTrue(createTokenResponse.statusCode() == 200);
        assertTrue(!createTokenResponse.jsonPath().get("request_token").equals(null));
        String session = createTokenResponse.jsonPath().get("request_token");

        //TODO: add Jersey Client, so we can get sessions approved through browser and then get users session using APIs
    }

}

