package com.NBWallet.layers.api.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Arrays;
import java.util.List;

public class ObjectConverter {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        // ✅ Добавляем поддержку Java 8 дат (LocalDateTime, LocalDate и т.п.)
        objectMapper.registerModule(new JavaTimeModule());

        // ✅ Отключаем преобразование дат в timestamp (иначе будут числа вместо ISO-строки)
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // ✅ Исключаем null-поля из JSON (удобно и чисто)
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> T convertJsonObjectToJavaObject(String jsonObject, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonObject, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка при парсинге JSON → объект: " + e.getMessage(), e);
        }
    }

    public static String convertJavaObjectToJsonObject(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка при конвертации объекта → JSON: " + e.getMessage(), e);
        }
    }

    public static <T> List<T> convertJsonArrayToListOfObj(String jsonArray, Class<T[]> clazz) {
        try {
            return Arrays.asList(objectMapper.readValue(jsonArray, clazz));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка при парсинге JSON массива: " + e.getMessage(), e);
        }
    }
}
