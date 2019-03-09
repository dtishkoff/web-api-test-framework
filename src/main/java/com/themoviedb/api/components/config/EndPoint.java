package com.themoviedb.api.components.config;

public interface EndPoint {

    String DISCOVER = "/discover/movie?";
    String MOVIES_GET_DETAILS = "/movie/{movie_id}?";
    String MOVIES_GET_REVIEWS = "/movie/{movie_id}/reviews?";
    String AUTH_CREATE_TOKEN = "authentication/token/new?";
    String AUTH_CREATE_SESSION = "authentication/session/new?";
    String LIST_CREATE = "/list?";
}
