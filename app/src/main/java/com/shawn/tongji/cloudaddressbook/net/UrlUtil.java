package com.shawn.tongji.cloudaddressbook.net;

import retrofit.RestAdapter;

public class UrlUtil {
    public static final int CONFLICT = 409;
    public static final int NOT_FOUND = 404;
    public static final int UNAUTHORIZED = 401;
    public static final int SERVER_ERROR = 500;

    public static final String ROOT_URL = "http://10.20.70.226:8080/CloudAddressBook/services/";
    public static final String NEW_ROOT_URL = "http://192.168.1.111:8080/AddressBookServices/services/";

    public static final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlUtil.NEW_ROOT_URL).build();

    public static RestAdapter getRestAdapter() {
        return restAdapter;
    }

    public static String getLoginUrl(String email, String password) {
        return ROOT_URL + "user/login?email=" + email + "&password=" + password;
    }

    public static String getRegistrationUrl(String name, String email, String password) {
        return ROOT_URL + "user/regist?email=" + email + "&password=" + password + "&name=" + name;
    }

    public static String getSaveUserContactorUrl() {
        return ROOT_URL + "user/saveUserContactor";
    }

    public static String getSelfInfoUrl(int userId) {
        return String.format(ROOT_URL + "user/getContactor?userid=%d",
                userId);
    }
}
