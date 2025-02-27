package com.jrymos.study.framework;

import com.jrymos.study.framework.classloader.MyClassLoader;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
//        Bootstrap.main(args);
        System.out.println(Main.class.getName());
        System.out.println(Main.class.getClassLoader());
        MyClassLoader myClassLoaderV1 = new MyClassLoader("v1");
        MyClassLoader myClassLoaderV2 = new MyClassLoader("v2");
        String name = "test.Number";
        Class<?> aClass = myClassLoaderV1.loadClass(name);
        Class<?> aClass1 = myClassLoaderV1.loadClass(name);
        Class<?> aClass2 = myClassLoaderV2.loadClass(name);

        System.out.println(aClass);
        System.out.println(aClass1);
        System.out.println(aClass2);
        System.out.println(aClass1 == aClass);
        System.out.println(aClass1 == aClass2);
        System.out.println(Objects.equals(aClass1, aClass2));

        Object v1TestNumber1 = aClass.getConstructors()[0].newInstance(1);
        Object v1TestNumber2 = aClass1.getConstructors()[0].newInstance(1);
        Object v2TestNumber1 = aClass2.getConstructors()[0].newInstance(1);
        Object v2TestNumber2 = aClass2.getConstructors()[0].newInstance(1);
        System.out.println(v1TestNumber1);
        System.out.println(v1TestNumber2);
        System.out.println(v2TestNumber1);
        System.out.println(v2TestNumber2);
        System.out.println(v1TestNumber1.equals(v1TestNumber2));
        System.out.println(v1TestNumber1.equals(v2TestNumber2));
        System.out.println(v2TestNumber1.equals(v2TestNumber2));

    }
}