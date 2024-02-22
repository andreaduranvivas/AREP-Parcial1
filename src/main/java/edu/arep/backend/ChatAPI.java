package edu.arep.backend;

import java.awt.image.VolatileImage;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.lang.*;
import java.util.Arrays;

import static java.lang.System.out;

public class ChatAPI {

    //  Retorna una lista de campos declarados y métodos declarados
    public static ArrayList<String> Class(String className){

        ArrayList<String> declarados = new ArrayList<>();

        Class<?> c = null;
        try {
            c = Class.forName(className);
            Object t = c.newInstance();

            Field[] allFields = className.getClass().getDeclaredFields();
            for (Field f : allFields) {
                String fname = f.toGenericString();
                declarados.add(fname);
            }

            Method[] allMethods = c.getDeclaredMethods();
            for (Method m : allMethods) {
                String mname = m.toGenericString();
                declarados.add(mname);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return declarados;
    }


    // Retorna el resultado de la invocación del método.
    public static String invoke(String className, String methodName){

        String resultado = "";

        try {
            Class<?> c = Class.forName(className);

            Method m = c.getMethod(methodName);
            resultado = (m.invoke(null)).toString();

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    //  retorna el resultado de la invocación del método
    public static String unaryInvoke(String className, String methodName, String paramType, String paramValue){

        String resultado = "";

        try {
            Class<?> c = Class.forName(className);

            if (paramType == "double"){
                Method m = c.getMethod(methodName, Double.TYPE);
                resultado = (m.invoke(null, Double.parseDouble(paramValue))).toString();
            } else if (paramType == "int") {
                Method m = c.getMethod(methodName, Integer.TYPE);
                resultado = (m.invoke(null, Integer.parseInt(paramValue))).toString();
            } else if (paramType == "String") {
                Method m = c.getMethod(methodName, String.class);
                resultado = (m.invoke(null, paramValue)).toString();
            }else {
                return "Método no válido";
            }

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    // retorna el resultado de la invocación del método
    public static String binaryInvoke(String className, String methodName, String paramType1, String paramValue1, String paramType2, String paramValue2){

        String resultado = "";

        try {
            Class<?> c = Class.forName(className);

            if (paramType1 == "double"){
                Method m = c.getMethod(methodName, Double.TYPE, Double.TYPE);
                resultado = (m.invoke(null, Double.parseDouble(paramValue1), Double.parseDouble(paramValue2))).toString();
            } else if (paramType1 == "int") {
                Method m = c.getMethod(methodName, Integer.TYPE, Integer.TYPE);
                resultado = (m.invoke(null, Integer.parseInt(paramValue1), Integer.parseInt(paramValue2))).toString();
            } else if (paramType1 == "String") {
                Method m = c.getMethod(methodName, String.class, String.class);
                resultado = (m.invoke(null, paramValue1, paramValue2)).toString();
            }else {
                return "Método no válido";
            }

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    public static void main(String[] args) {
        //System.out.println(Class("edu.arep.backend.HttpServer"));
        //System.out.println(invoke("java.lang.System", "getenv"));
        //System.out.println(unaryInvoke("java.lang.Math", "abs", "int", "3"));
        //System.out.println(unaryInvoke("java.lang.Integer", "valueOf", "String", "3"));
        //System.out.println(binaryInvoke("java.lang.Math", "max", "double", "4.5", "double", "-3.7"));
        System.out.println(binaryInvoke("java.lang.Integer",  "min", "int", "6", "int", "-3"));
    }


}
