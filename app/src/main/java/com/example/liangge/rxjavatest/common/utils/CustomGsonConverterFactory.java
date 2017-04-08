package com.example.liangge.rxjavatest.common.utils;

import com.google.gson.Gson;

import retrofit2.Converter;

/**
 * Created by guhongliang on 2017/3/27.
 */

public class CustomGsonConverterFactory extends Converter.Factory {
    private final Gson gson;

    public CustomGsonConverterFactory(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson==null");
        }
        this.gson = gson;
    }

    public static CustomGsonConverterFactory create(Gson gson) {
        return create(new Gson());

    }


}
