package org.springframework.aop.framework;

import com.example.usestaticadvice.aspect.MyAspect0;
import com.example.usestaticadvice.bean.Advice1;
import com.example.usestaticadvice.bean.Advice2;
import com.example.usestaticadvice.bean.MyInvocation;
import com.example.usestaticadvice.bean.Target;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.*;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.*;
import org.springframework.aop.interceptor.ExposeInvocationInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : KaelvihN
 * @date : 2023/9/2 23:32
 */
public class A0 {
    public static void main(String[] args) throws Throwable {
//        test0();
        test1();
    }

    public static void test0() throws Throwable {
        //切面对象示例工厂，用于反射调用切面中的方法
        SingletonAspectInstanceFactory factory =
                new SingletonAspectInstanceFactory(new MyAspect0());
        //存放低级切面的集合
        List<Advisor> advisors = new ArrayList<>();
        /**
         * 高级切面转为低级切面
         */
        //遍历MyAspect0中的方法
        for (Method method : MyAspect0.class.getDeclaredMethods()) {
            //解析方法上的注解
            if (method.isAnnotationPresent(Before.class)) {
                //解析切点
                String expression = method.getAnnotation(Before.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                //通知类
                Advice advice =
                        new AspectJMethodBeforeAdvice(method, pointcut, factory);
                //有通知，切点 => 创建切面
                DefaultPointcutAdvisor advisor =
                        new DefaultPointcutAdvisor(pointcut, advice);
                //将创建的低级切面放入集合
                advisors.add(advisor);
            } else if (method.isAnnotationPresent(After.class)) {
                //解析切点
                String expression = method.getAnnotation(After.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                //通知类
                Advice advice =
                        new AspectJAfterAdvice(method, pointcut, factory);
                //有通知，切点 => 创建切面
                DefaultPointcutAdvisor advisor =
                        new DefaultPointcutAdvisor(pointcut, advice);
                //将创建的低级切面放入集合
                advisors.add(advisor);
            } else if (method.isAnnotationPresent(AfterReturning.class)) {
                //解析切点
                String expression = method.getAnnotation(AfterReturning.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                //通知类
                Advice advice =
                        new AspectJAfterReturningAdvice(method, pointcut, factory);
                //有通知，切点 => 创建切面
                DefaultPointcutAdvisor advisor =
                        new DefaultPointcutAdvisor(pointcut, advice);
                //将创建的低级切面放入集合
                advisors.add(advisor);
            } else if (method.isAnnotationPresent(AfterThrowing.class)) {
                //解析切点
                String expression = method.getAnnotation(AfterThrowing.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                //通知类
                Advice advice =
                        new AspectJAfterThrowingAdvice(method, pointcut, factory);
                //有通知，切点 => 创建切面
                DefaultPointcutAdvisor advisor =
                        new DefaultPointcutAdvisor(pointcut, advice);
                //将创建的低级切面放入集合
                advisors.add(advisor);
            } else if (method.isAnnotationPresent(Around.class)) {
                //解析切点
                String expression = method.getAnnotation(Around.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                //通知类
                Advice advice =
                        new AspectJAroundAdvice(method, pointcut, factory);
                //有通知，切点 => 创建切面
                DefaultPointcutAdvisor advisor =
                        new DefaultPointcutAdvisor(pointcut, advice);
                //将创建的低级切面放入集合
                advisors.add(advisor);
            }
        }
        //遍历集合
        advisors.forEach(System.out::println);
        System.out.println(">>>>>>>>>>");
        /**
         * 统一转换为MethodInterceptor
         */
        ProxyFactory proxyFactory = new ProxyFactory();
        Target target = new Target();
        //准备MethodInvocation放入当前线程
        proxyFactory.addAdvice(ExposeInvocationInterceptor.INSTANCE);
        proxyFactory.addAdvisors(advisors);
        proxyFactory.setTarget(target);
        Method method = Target.class.getMethod("foo");
        //转换
        List<Object> methodInterceptorList =
                proxyFactory.getInterceptorsAndDynamicInterceptionAdvice(method, Target.class);
        //遍历
        methodInterceptorList.forEach(System.out::println);
        System.out.println(">>>>>>>>>>");
        //创建并执行调用链
        MethodInvocation methodInvocation = new ReflectiveMethodInvocation(
                null, target, method, new Object[0], Target.class, methodInterceptorList
        );
        methodInvocation.proceed();
    }

    public static void test1() throws Throwable {
        Target target = new Target();
        Method method = Target.class.getMethod("foo");
        List<MethodInterceptor> methodInterceptorList = new ArrayList<>(
                Arrays.asList(new Advice1(), new Advice2())
        );
        MyInvocation myInvocation = new MyInvocation(target, method, new Object[0], methodInterceptorList);
        myInvocation.proceed();
    }
}
