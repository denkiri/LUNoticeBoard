package com.example.dennis.lunoticeboard;

public class Constants {
    public class ServiceType {
        public static final String BASE_URL = "http://192.168.43.237/laikipia/";
        public static final String LOGIN = BASE_URL + "adminlogin.php";
        public static final String LOGINSEC = BASE_URL + "seclogin.php";
        public static final String REGISTER =  BASE_URL + "register.php";
    }
    // webservice key constants
    public class Params {
        public static final String NAME = "name";
        public static final String SCHOOL = "school";
        public static final String DEPARTMENT = "department";
        public static final String CURRENT = "current";
        public static final String ID ="id";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
    }
}
