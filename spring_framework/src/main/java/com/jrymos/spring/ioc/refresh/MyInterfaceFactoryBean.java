package com.jrymos.spring.ioc.refresh;

import com.jrymos.spring.ioc.utils.PrintUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

@FieldNameConstants
public class MyInterfaceFactoryBean<T> implements FactoryBean<T> {




    @Getter
    @Setter
    private String beanName;

    private Class<T> interfaceClass;

    private T bean;

    public MyInterfaceFactoryBean() {
        //intentionally empty
        PrintUtils.errorLog();
    }


    public MyInterfaceFactoryBean(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
        PrintUtils.errorLog();
    }


    @Override
    public T getObject() throws Exception {
        PrintUtils.errorLog(beanName, interfaceClass.getName());
        if (bean != null) {
            return bean;
        }
        synchronized (this) {
            if (bean != null) {
                return bean;
            }
            bean = (T) Proxy.newProxyInstance(
                    interfaceClass.getClassLoader(),
                    new Class<?>[]{interfaceClass},
                    new MyInterfaceProxy(beanName)
            );
        }
        return bean;
    }
    @Override
    public final Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public final boolean isSingleton() {
        return true;
    }
}
