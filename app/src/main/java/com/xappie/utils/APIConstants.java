package com.xappie.utils;

/**
 * Created by shankar on 4/30/2017.
 */

public class APIConstants {
    public enum REQUEST_TYPE {
        GET, POST, MULTIPART_GET, MULTIPART_POST, DELETE, PUT, PATCH
    }

    private static final String STATUS = "status";
    public final static String SERVER_NOT_RESPONDING = "We are unable to connect the internet. " +
            "Please check your connection and try again.";

    public static String ERROR_MESSAGE = "We could not process your request at this time. Please try again later.";

    public static String BASE_URL = "http://test.xappie.com/api/";
    public static String GET_LANGUAGES = BASE_URL + "get_languages";
    public static String GET_COUNTRIES = BASE_URL + "get_countries";
    public static String GET_STATES = BASE_URL + "get_states";
    public static String GET_CITIES = BASE_URL + "get_cities";
    public static String GET_VIDEOS = BASE_URL + "get_videos";

}
