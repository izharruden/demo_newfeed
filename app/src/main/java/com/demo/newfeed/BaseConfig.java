package com.demo.newfeed;

public final class BaseConfig {

    static {
        System.loadLibrary("keys");
    }

    public static native String API_KEY();

    public static native String BASE_URL();
}
