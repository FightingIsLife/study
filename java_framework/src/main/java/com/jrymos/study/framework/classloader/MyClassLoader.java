package com.jrymos.study.framework.classloader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.SecureClassLoader;
import java.util.Objects;

public class MyClassLoader extends SecureClassLoader {

    private final String version;

    public MyClassLoader(String version) {
        this.version = version;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name.equals("test.Number")) {
            // 构建 Number.class 文件的完整路径
            String filePath =  "D:\\code\\jrymos\\github\\study\\java_framework\\lib\\test\\" + version + "\\Number.class";
            try (InputStream input = new FileInputStream(filePath)) {
                // 读取类文件的字节
                byte[] classData = input.readAllBytes();
                // 定义并返回类
                return defineClass(name, classData, 0, classData.length);
            } catch (IOException e) {
                throw new ClassNotFoundException("Could not load class: " + name, e);
            }
        }
        throw new ClassNotFoundException(name);
    }


    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        System.out.println(MyClassLoader.class);
        System.out.println(MyClassLoader.class.getClassLoader());
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
        System.out.println(aClass1.getClassLoader());
        System.out.println(aClass2.getClassLoader());

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
