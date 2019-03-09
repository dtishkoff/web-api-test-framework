package com.themoviedb.api.tests.util;

import java.io.*;
import java.nio.file.Paths;
import java.util.Date;

import com.jayway.restassured.path.json.JsonPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author dtishkoff
 */

public class Config {

    public void writeJSONtoFile(JSONObject jsonObject) throws IOException {
        int currentTime = (int) (new Date().getTime()/1000);
        String workingDir = System.getProperty("user.dir");
        String fileName = workingDir+"/src/main/resources/data/movies.txt";

        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName, true), "UTF-8"));

//        try (FileWriter file = new FileWriter(fileName,true)) {
//            file.write(jsonObject.toString());
//        }
    }
}