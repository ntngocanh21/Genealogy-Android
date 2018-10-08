package com.senior.project.genealogy.util;

public class Constants {
    public static String BASE_URL = "http://10.0.8.90:8080";
    public static String SHARED_PREFERENCES_NAME = "genealogy";
    public class HTTPCodeResponse {
        public static final int SUCCESS = 0;
        public static final int OBJECT_EXISTED = 302;
        public static final int OBJECT_NOT_FOUND = 404;
    }
}
