package com.themoviedb.api.components;

import java.util.Properties;

public class BaseAPI {

    //TODO: the plan for this class is to have reusable base methods
    private Properties properties;

    protected void setProperties(Properties properties) {
        this.properties = properties;
    }
}
