package com.github.school.utils;

import com.github.school.annotations.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Loggers {

    public static void log(Object obj) {
        Class<?> clazz = obj.getClass();
        logFields(obj, clazz);
    }

    private static void logFields(Object obj, Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();

        System.out.println(fields.length + " fields");
        System.out.println(clazz + " class");

        Map<String, String> logData = new HashMap<>();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Logger.class)) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(obj);

                    // Adiciona apenas valores primitivos ao logData
                    if (value != null && isPrimitive(value.getClass())) {
                        logData.put(field.getName(), value.toString());
                    } else {
                        Class<?> superClass = clazz.getSuperclass();
                        logFields(obj, superClass);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(logData);

        // Recursively log fields of nested classes
//        Class<?> superClass = clazz.getSuperclass();
//        if (superClass != null) {
//            logFields(obj, superClass);
//        }
    }

    private static boolean isPrimitive(Class<?> type) {
        return type.isPrimitive() ||
                type == Boolean.class ||
                type == Character.class ||
                type == Byte.class ||
                type == Short.class ||
                type == Integer.class ||
                type == Long.class ||
                type == Float.class ||
                type == String.class ||
                type == Double.class;
    }

}
