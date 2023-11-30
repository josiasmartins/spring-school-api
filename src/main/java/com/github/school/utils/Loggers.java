package com.github.school.utils;

import com.github.school.annotations.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Loggers {

    public static void log(Object obj) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        System.out.println(fields + " fields");
        System.out.println(clazz + " class");

        Map<String, Object> logData = new HashMap<>();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Logger.class)) {
                try {
                    field.setAccessible(true);
                    logData.put(field.getName(), field.get(obj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(logData);
    }

}
