package com.wr.jvm.proxy;

import java.lang.reflect.*;

/**
 * @author: Alice
 * @date: 2018/7/19.
 * @since: 1.0.0
 */
public class DynamicProxy01 {
    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();    //1.创建委托对象
        ProxyHandler handler = new ProxyHandler(realSubject);   //2.创建调用处理器对象
        Subject proxySubject = (Subject) Proxy.newProxyInstance(RealSubject.class.getClassLoader(),
                RealSubject.class.getInterfaces(), handler);    //3.动态生成代理对象
        System.out.println(proxySubject.getClass().getName());
        Class cl = proxySubject.getClass();
        Constructor[] con1 = cl.getConstructors();
        for (Constructor con:con1){
            System.out.print(con.getName()+"{");
            Parameter[] parameters = con.getParameters();
            for (Parameter p : parameters){
                System.out.print(p.getType().getName()+" "+p.getName());
            }
            System.out.println("}");
        }
        Method[] m1 = cl.getDeclaredMethods();
        for (Method method : m1) {
            System.out.print(method.getName()+"{");
            Parameter[] parameters = method.getParameters();
            for (Parameter p : parameters){
                System.out.print(p.getType().getName()+" "+p.getName());
            }
            System.out.println("}");
        }
        Field[] f1 = cl.getDeclaredFields();
        for (Field field : f1){
            System.out.println(field.getType().getName()+" "+field.getName());
        }
        proxySubject.request(); //4.通过代理对象调用方法
    }
}

interface Subject{
    void request();
}

/**
 * 委托类
 */
class RealSubject implements Subject{
    public void request(){
        System.out.println("====RealSubject Request====");
    }
}
/**
 * 代理类的调用处理器
 */
class ProxyHandler implements InvocationHandler {
    private Subject subject;
    public ProxyHandler(Subject subject){
        this.subject = subject;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        System.out.println("====before====");//定义预处理的工作，当然你也可以根据 method 的不同进行不同的预处理工作
        Object result = method.invoke(subject, args);
        System.out.println("subject class" + subject.getClass().getName());
        System.out.println("proxy class" + proxy.getClass().getName());
        System.out.println("====after====");
        return result;
    }
}