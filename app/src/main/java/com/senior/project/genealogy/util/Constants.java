package com.senior.project.genealogy.util;

public class Constants {

//    public static String BASE_URL = "http://10.0.2.2:3000/";
    public static String BASE_URL = "http://10.0.12.78:8080";

    public static String SHARED_PREFERENCES_NAME = "genealogy";

    public static class SHARED_PREFERENCES_KEY {
        public static String TOKEN = "token";
        public static String USERNAME = "username";
        public static String PASSWORD = "password";
        public static String AVATAR = "avatar";
        public static String FULLNAME = "fullname";
        public static String DEVICE_ID = "device_id";
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

    public class ROLE {
        public static final int ADMIN_ROLE = 1;
        public static final int MOD_ROLE = 2;
        public static final int MEMBER_ROLE = 3;
        public static final int GUEST_ROLE = 4;
    }

    public class RELATIVE_TYPE {
        public static final String SON = "Son";
        public static final String DAUGHTER = "Daughter";
        public static final String BROTHER = "Brother";
        public static final String SISTER = "Sister";
    }

    public static final int DOUBLE_CLICK_TIME_DELTA = 400;
}
