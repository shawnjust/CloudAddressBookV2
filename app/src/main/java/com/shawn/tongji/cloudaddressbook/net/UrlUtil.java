package com.shawn.tongji.cloudaddressbook.net;

import retrofit.RestAdapter;

public class UrlUtil {
    public static final int CONFLICT = 409;
    public static final int NOT_FOUND = 404;
    public static final int UNAUTHORIZED = 401;
    public static final int SERVER_ERROR = 500;

    public static final String NEW_ROOT_URL = "http://10.0.3.2:8080/com-addressbook-app/services/";

    public static final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlUtil.NEW_ROOT_URL).build();

    public static RestAdapter getRestAdapter() {
        return restAdapter;
    }

    public static String getImageView(int idA, String fileName) {
        return NEW_ROOT_URL + "image/" + idA + "?file=" + fileName;
    }
}
