package com.themoviedb.api.tests;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;

import com.saucedemo.web.componenets.PropertyLoader;
import com.themoviedb.api.components.config.EndPoint;
import com.themoviedb.api.tests.util.Config;
import org.junit.Before;

import java.io.IOException;
import java.util.*;

import static com.jayway.restassured.RestAssured.given;


public class BaseAPITMDTest extends Config{

    public static RequestSpecification tmd_requestSpec;
    public static ResponseSpecification responseSpec;

    private String siteURL;
    public String api_key;
    public String title;
    public String releaseDate;
    public String id;

    private Properties properties;

    @Before
    public void setup() throws Exception {
        setProperties(loadProperties());

        this.siteURL = properties.getProperty("server.protocol")+"://" + properties.getProperty("server.hostname");
        this.api_key = properties.getProperty("api_key");

        createResponseSpecification(this.siteURL,"3","application/json");

        int movieDataSetIndex =  getRandomValue(19);

        Map<String,String> movieMap = discoverMovies().get(movieDataSetIndex);
        this.title = movieMap.get("title");
        this.releaseDate = movieMap.get("release_date");
        this.id = String.valueOf(movieMap.get("id"));
    }


    private void createResponseSpecification(String uri, String version, String contentType) {
        tmd_requestSpec = new RequestSpecBuilder().
                setBaseUri(uri).
                setBasePath(version).
                addHeader("Content-Type", contentType).
                build();

        RestAssured.requestSpecification = tmd_requestSpec;

        responseSpec = new ResponseSpecBuilder().build();

        RestAssured.responseSpecification = responseSpec;
    }

    public List<Map<String,String>> discoverMovies() throws IOException{

        String pageNumber = String.valueOf(getRandomValue(100));

        String params = "&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page="+pageNumber;
        Response response =
                given().
                        spec(tmd_requestSpec).
                        when().get(EndPoint.DISCOVER+"api_key="+this.api_key+params).
                        then().log().all().
                        extract().response();

        return response.jsonPath().getList("results");
    }

    protected void setProperties(Properties properties) {
        this.properties = properties;
    }

    protected ArrayList<String> getPropertyFileList() {
        ArrayList<String> propFileList = new ArrayList<String>();
        propFileList.add("/apiTest.properties");
        return propFileList;
    }

    protected Properties loadProperties() {
        ArrayList<String> propFileList = getPropertyFileList();
        try {
            return PropertyLoader.loadDefaultAndCustomProps(propFileList);
        } catch (IOException e) {
            throw new RuntimeException("Could not load properties from " + propFileList, e);
        }
    }

    private int getRandomValue(int number) {
        return (int)(Math.random()*number);
    }
}
