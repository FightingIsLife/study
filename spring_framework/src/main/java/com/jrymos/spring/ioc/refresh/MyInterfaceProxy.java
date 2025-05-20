package com.jrymos.spring.ioc.refresh;

import com.jrymos.spring.ioc.utils.PrintUtils;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInterfaceProxy  implements InvocationHandler  {

    public MyInterfaceProxy(String beanName) {
        this.beanName = beanName;
        PrintUtils.errorLog(beanName);
    }

    private final String beanName;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        PrintUtils.errorLog(proxy.getClass().getName(), method.getName());
        return beanName;
    }
}
