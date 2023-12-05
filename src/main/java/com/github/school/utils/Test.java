package com.github.school.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Test {

    public static Map<String, String> scanPropriedades(Object objeto) {
        Map<String, String> mapaDePropriedades = new HashMap<>();
        scanPropriedadesRecursivamente(objeto, "", mapaDePropriedades);
        return mapaDePropriedades;
    }

    private static void scanPropriedadesRecursivamente(Object objeto, String prefixo, Map<String, String> mapaDePropriedades) {
        if (objeto == null) {
            return;
        }

        Class<?> classe = objeto.getClass();
        Field[] campos = classe.getDeclaredFields();

        for (Field campo : campos) {
            campo.setAccessible(true);

            try {
                Object valor = campo.get(objeto);
                String nomePropriedade = prefixo.isEmpty() ? campo.getName() : prefixo + "." + campo.getName();

                if (isTipoSimples(valor)) {
                    mapaDePropriedades.put(nomePropriedade, valor != null ? valor.toString() : "null");
                } else if (valor != null && valor.getClass().isArray()) {
                    int length = java.lang.reflect.Array.getLength(valor);
                    for (int i = 0; i < length; i++) {
                        scanPropriedadesRecursivamente(java.lang.reflect.Array.get(valor, i), nomePropriedade + "[" + i + "]", mapaDePropriedades);
                    }
                } else {
                    // Se o valor não for simples ou array, chama recursivamente
                    scanPropriedadesRecursivamente(valor, nomePropriedade, mapaDePropriedades);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isTipoSimples(Object valor) {
        return valor instanceof String || valor instanceof Integer || valor instanceof Boolean;
    }

}
