package com.NBWallet.layers.api.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

public class ObjectConverter {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T convertJsonObjectToJavaObject(String jsonObject, Class <T> clazz) {
        try {
            return objectMapper.readValue(jsonObject, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String convertJavaObjectToJsonObject(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> convertJsonArrayToListOfObj(String jsonArray, Class<T[]> clazz) {
        try {
            return Arrays.asList(objectMapper.readValue(jsonArray, clazz));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
