package com.fireflyingup.deerlet.common;

import com.google.gson.Gson;

public class GsonUtils {

    public static final Gson gson = new Gson();

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

}
