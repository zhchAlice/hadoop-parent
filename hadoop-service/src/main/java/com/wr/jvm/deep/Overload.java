package com.wr.jvm.deep;

/**
 * @author: Alice
 * @date: 2018/10/22.
 * @since: 1.0.0
 */
public class Overload {
    static abstract class Human{}
    static class Man extends Human{}
    static class Woman extends Human{}

    public void sayHello(Human human){
        System.out.println("Human sayHello");
    }
    public void sayHello(Man man){
        System.out.println("Man sayHello");
    }
    public void sayHello(Woman woman){
        System.out.println("Woman sayHello");
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        Overload overload = new Overload();
        overload.sayHello(man);
        overload.sayHello(woman);
    }
}
