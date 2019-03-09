package com.themoviedb.api.tests.services;

import com.google.gson.JsonObject;
import com.jayway.restassured.response.Response;
import com.themoviedb.api.components.config.EndPoint;
import com.themoviedb.api.tests.BaseAPITMDTest;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class ListsIT extends BaseAPITMDTest {

    @Test
    public void testCreateListWihtoutProperAuth() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "My First Movie List");
        jsonObject.addProperty("description", "testing list creation");
        jsonObject.addProperty("language", "en");

        Response createListResponse =
                given().
                        spec(tmd_requestSpec).
                        body(jsonObject.toString()).
                        when().post(EndPoint.LIST_CREATE+"api_key="+api_key).
                        then().log().all().
                        extract().response();

        assertEquals(createListResponse.jsonPath().get("status_message"),"Authentication failed: You do not have permissions to access the service.");
    }

    @Test
    public void testCreateListW() {
        //TODO: with correct Authorization new List should be created - need to reuse that id in other tests.
    }

    @Test
    public void testAddMovie() {
        //TODO: get a list number, add new movie to that list. Verify list has been updated. Need to craete a list first
    }

    @Test
    public void testClearList() {
        //TODO: test clearing list, verify all data is removed.
    }

    @Test
    public void testDeleteList() {
        //TODO: delete existing list - verify list has been removed
    }

    @Test
    public void testDeleteNonExistingList() {
        //TODO: try to delete list with non existing id - verify proper codes, error messages are return
    }

    //TODO: this method will be called from the above tests to create a new test and get the ID
    private int createList() {

        return 0;
    }

    //TODO: this method will be called from the above tests to get list under test
    private void getDetails(int id) {

    }
}
