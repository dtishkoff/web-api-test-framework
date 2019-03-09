package com.themoviedb.api.tests.services;

import com.jayway.restassured.response.Response;
import com.themoviedb.api.components.config.EndPoint;
import com.themoviedb.api.tests.BaseAPITMDTest;
import static org.junit.Assert.*;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

public class MoviesIT extends BaseAPITMDTest {


    private String params = "&language=en-US";

    @Test
    public void testGetDetails() {

        Response response =
                given().
                        pathParam("movie_id", id).
                        spec(tmd_requestSpec).
                        when().get(EndPoint.MOVIES_GET_DETAILS+"api_key="+api_key+params).
                        then().log().all().
                        extract().response();

        assertTrue(response.statusCode() == 200);
        assertTrue(response.jsonPath().get("title").equals(title));
    }

    @Test
    public void testGetReviews() {
        Response response =
                given().
                        pathParam("movie_id", id).
                        spec(tmd_requestSpec).
                        when().get(EndPoint.MOVIES_GET_REVIEWS+"api_key="+api_key+params).
                        then().log().all().
                        extract().response();

        assertTrue(response.statusCode() == 200);
        assertTrue(response.jsonPath().get("id").equals(Integer.valueOf(id)));
    }
}
