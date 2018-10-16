package com.senior.project.genealogy.util;

public class Constants {
    public static String BASE_URL = "http://10.0.13.73:8080";
    public static String SHARED_PREFERENCES_NAME = "genealogy";
    public class HTTPCodeResponse {
        public static final int SUCCESS = 0;
        public static final int OBJECT_EXISTED = 302;
        public static final int OBJECT_NOT_FOUND = 404;
        public static final int UNAUTHORIZED = 401;
    }
}
