package com.miteshpv.bizprofileservice.bizprofile.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class Utility <T> {

    public static <T> void copyObject(T destination, T source) throws IllegalAccessException, NoSuchFieldException {
        for (Field field : source.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(source);

            if (null != value)
            {
                Field destField = destination.getClass().getDeclaredField(name);
                destField.setAccessible(true);
                destField.set(destination, value);
            }
            log.info("Field name: %s, Field value: %s%n", name, value);
        }
    }
}
