package com.example.usestaticadvice.bean;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author : KaelvihN
 * @date : 2023/9/5 1:04
 */
public class MyInvocation implements MethodInvocation {
    private final Object target;
    private final Method method;
    private final Object[] args;
    private final List<MethodInterceptor> methodInterceptorList;
    private int count = 0;

    public MyInvocation(Object target, Method method, Object[] args, List<MethodInterceptor> methodInterceptorList) {
        this.target = target;
        this.method = method;
        this.args = args;
        this.methodInterceptorList = methodInterceptorList;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object[] getArguments() {
        return this.args;
    }

    @Override
    public Object proceed() throws Throwable {
        if (count >= methodInterceptorList.size()) {
            //调用目标，结束递归并返回
            return method.invoke(target, args);
        }
        //注意调用通知
        MethodInterceptor methodInterceptor = methodInterceptorList.get(count++);
        //递归操作交给通知类
        return methodInterceptor.invoke(this);
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }
}
