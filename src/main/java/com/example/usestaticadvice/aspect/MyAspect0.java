package com.example.usestaticadvice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * @author : KaelvihN
 * @date : 2023/9/2 23:23
 */

public class MyAspect0 {

    @Before("execution(* foo())")
    public void before() {
        System.out.println("Before1");
    }

    @After("execution(* foo())")
    public void after() {
        System.out.println("After");
    }

    @AfterReturning("execution(* foo())")
    public void afterReturning() {
        System.out.println("AfterReturning");
    }

    @AfterThrowing("execution(* foo())")
    public void afterThrowing() {
        System.out.println("AfterThrowing");
    }

    @Around("execution(* foo())")
    public Object around(ProceedingJoinPoint point) {
        try {
            System.out.println("Before Around");
            return point.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("After Around");
        }
    }
}

