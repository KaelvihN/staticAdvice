package com.example.usestaticadvice.bean;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author : KaelvihN
 * @date : 2023/9/5 0:58
 */
public class Advice2 implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("Advice2.before()");
        Object result = invocation.proceed();
        System.out.println("Advice2.after()");
        return result;
    }
}
