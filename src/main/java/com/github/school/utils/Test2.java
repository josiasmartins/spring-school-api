package com.github.school.utils;

import com.github.school.annotations.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Test2 {

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

            // Verifica se o campo está anotado com @Logger
            if (campo.isAnnotationPresent(Logger.class)) {
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
                    } else if (isColecaoImutavel(valor)) {
                        // Se for uma coleção imutável, cria uma cópia mutável antes de explorar
                        Object colecaoMutavel = criarCopiaMutavel(valor);
                        scanPropriedadesRecursivamente(colecaoMutavel, nomePropriedade, mapaDePropriedades);
                    } else {
                        // Se o valor não for simples, array ou coleção imutável, chama recursivamente
                        scanPropriedadesRecursivamente(valor, nomePropriedade, mapaDePropriedades);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static boolean isTipoSimples(Object valor) {
        return valor instanceof String || valor instanceof Integer || valor instanceof Boolean;
    }

    private static boolean isColecaoImutavel(Object valor) {
        // Verifica se o valor é uma instância de uma coleção imutável
        return valor != null && valor.getClass().getName().startsWith("java.util.ImmutableCollections$");
    }

    private static Object criarCopiaMutavel(Object valor) {
        // Cria uma cópia mutável da coleção imutável
        // (Este método precisa ser ajustado dependendo do tipo de coleção imutável que está sendo usada)
        // Aqui, estamos assumindo que o valor é uma lista imutável
        java.util.List<?> listaImutavel = (java.util.List<?>) valor;
        return new java.util.ArrayList<>(listaImutavel);
    }


}
