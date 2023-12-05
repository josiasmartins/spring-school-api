package com.github.school.utils;

import com.github.school.annotations.Logger;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class Teste3 {

    public static Map<String, String> scanPropriedades(Object objeto) {
        Map<String, String> mapaDePropriedades = new HashMap<>();
        scanPropriedadesRecursivamente(objeto, "", mapaDePropriedades);
        System.out.println(mapaDePropriedades);
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

            // Ignorar campos estáticos, finais e sintéticos
            if (isCampoIgnoravel(campo)) {
                continue;
            }

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
                    } else if (valor instanceof List<?>) {
                        // Se for uma lista, itera sobre os elementos da lista
                        List<?> lista = (List<?>) valor;
                        for (int i = 0; i < lista.size(); i++) {
                            scanPropriedadesRecursivamente(lista.get(i), nomePropriedade + "[" + i + "]", mapaDePropriedades);
                        }
                    } else if (isColecaoImutavel(valor)) {
                        // Se for uma coleção imutável, cria uma cópia mutável antes de explorar
                        Object colecaoMutavel = criarCopiaMutavel(valor);
                        scanPropriedadesRecursivamente(colecaoMutavel, nomePropriedade, mapaDePropriedades);
                    } else {
                        // Se o valor não for simples, array, lista ou coleção imutável, chama recursivamente
                        scanPropriedadesRecursivamente(valor, nomePropriedade, mapaDePropriedades);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static boolean isTipoSimples(Object valor) {
        return (
                valor instanceof String ||
                        valor instanceof Number ||
                        valor instanceof Boolean ||
                        valor == null
        );
    }

    private static boolean isColecaoImutavel(Object valor) {
        // Verifica se o valor é uma instância de uma coleção imutável
        return valor != null && valor.getClass().getName().startsWith("java.util.ImmutableCollections$");
    }

    private static Object criarCopiaMutavel(Object valor) {
        // Cria uma cópia mutável da coleção imutável
        // (Este método precisa ser ajustado dependendo do tipo de coleção imutável que está sendo usada)
        if (valor instanceof List) {
            List<?> listaImutavel = (List<?>) valor;
            return new java.util.ArrayList<>(listaImutavel);
        } // Adicione outros casos conforme necessário

        return valor; // Se não for uma coleção conhecida, retorna o valor original
    }

    private static boolean isCampoIgnoravel(Field campo) {
        int mod = campo.getModifiers();
        return Modifier.isStatic(mod) || Modifier.isFinal(mod) || campo.isSynthetic();
    }
}
