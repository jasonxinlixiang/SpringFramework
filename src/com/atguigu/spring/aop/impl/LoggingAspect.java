package com.atguigu.spring.aop.impl;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

//把这个类声明为一个切面：需要把该类放入到IOC容器中、再声明为一个切面
@Order(2)
@Component
@Aspect
public class LoggingAspect {

    /**
     * 定义一个方法，用于声明切入点表达式。一般的，该方法中不需要添入其他的代码
     * 使用@Pointcut 来声明切入点表达式
     */
    @Pointcut("execution(* com.atguigu.spring.aop.impl.ArithmeticCalculatorImpl.*(..))")
    public void declareJointPointExpression(){ }

    //声明该方法是一个前置通知：在目标方法开始之前执行
    @Before("declareJointPointExpression()")
    public void beforeMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("The method " + methodName + " begins with " + args);
    }

    //后置通知：在目标方法执行后（无论是否发生异常），执行的通知。
    //在后置通知中还不能访问目标方法执行的结果。
    @After("declareJointPointExpression()")
    public void afterMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("The method " + methodName + " ends " );
    }

    /**
     * 在方法正常结束后执行的代码
     * 返回通知是可以访问到方法的返回值的！
     * @param joinPoint
     */
    @AfterReturning(value = "declareJointPointExpression())",
        returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("The method " + methodName + " ends with " +  result);
    }

    /**
     * 在目标方法出现异常时会执行的代码。
     * 而且可以访问到异常对象；且可以指定在出现特定异常时在执行通知代码
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(value = "declareJointPointExpression()",
        throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("The method " + methodName + " throws exception: " +  ex);
    }

    /**
     * 环绕通知需要携带ProceedingJoinPoint 类型的参数
     * 环绕通知类似于动态代理的全过程：ProceedingJoinPoint 类型的参数可以决定是否执行目标方法
     * 且环绕通知必须有返回值，返回值即为目标方法的返回值
     */
    /*@Around("execution(* com.atguigu.spring.aop.impl.ArithmeticCalculatorImpl.*(..))")
    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint){

        Object result = null;
        String methodName = proceedingJoinPoint.getSignature().getName();

        try{
            //前置通知
            System.out.println("The method " + methodName + " begins with " + Arrays.asList(proceedingJoinPoint.getArgs()));
            //执行目标方法
            result = proceedingJoinPoint.proceed();
            //后置通知
            System.out.println("The method " + methodName + " ends with " + result);
        }catch (Throwable e){
            //异常通知
            System.out.println("The method occurs exception " + e);
            throw new RuntimeException(e);
        }
        //后置通知
        System.out.println("The method " + methodName + " ends with " +  result);
        return result;
    }*/
}
