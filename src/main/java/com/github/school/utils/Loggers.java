package com.github.school.utils;

import com.github.school.annotations.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Loggers {

    public static Map<String, String> log(Object obj) {
        Map<String, String> logData = new HashMap<>();
        logFields(obj, obj.getClass(), logData);
        return logData;
    }

    private static void logFields(Object obj, Class<?> clazz, Map<String, String> logData) {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);  // Torna o campo acessível

            try {
                Object value = field.get(obj);

                if (isSimpleValue(value)) {
                    logData.put(field.getName(), value != null ? value.toString() : "null");
                } else if (value != null && !field.getType().isPrimitive()) {
                    logFields(value, value.getClass(), logData);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Recursivamente loga campos de classes internas
        Class<?>[] innerClasses = clazz.getDeclaredClasses();
        for (Class<?> innerClass : innerClasses) {
            logFields(obj, innerClass, logData);
        }
    }

    private static boolean isSimpleValue(Object value) {
        return value instanceof String || value instanceof Number || value instanceof Boolean;
    }

}
