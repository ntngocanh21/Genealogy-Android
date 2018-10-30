package com.senior.project.genealogy.util;

public class Constants {

//    public static String BASE_URL = "http://10.0.2.2:3000/";
    public static String BASE_URL = "http://10.0.13.19:8080";

    public static String SHARED_PREFERENCES_NAME = "genealogy";

    public static class SHARED_PREFERENCES_KEY {
        public static String TOKEN = "token";
        public static String USERNAME = "username";
        public static String PASSWORD = "password";
    }

    /**
     * Empty String
     */
    public static String EMPTY_STRING = "";

    public class HTTPCodeResponse {
        public static final int SUCCESS = 0;
        public static final int OBJECT_EXISTED = 302;
        public static final int OBJECT_NOT_FOUND = 404;
        public static final int UNAUTHORIZED = 401;
    }
}
