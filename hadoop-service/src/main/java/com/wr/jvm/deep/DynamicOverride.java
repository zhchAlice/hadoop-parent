package com.wr.jvm.deep;

/**
 * @author: Alice
 * @date: 2018/10/22.
 * @since: 1.0.0
 */
public class DynamicOverride {
    static abstract class Human{
        protected abstract void sayHello();
    }
    static class Man extends Human{
        @Override
        protected void sayHello() {
            System.out.println("Man sayHello");
        }
    }

    static class Woman extends Human{

        @Override
        protected void sayHello() {
            System.out.println("Woman sayHello");
        }
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        man.sayHello();
        woman.sayHello();
    }
}
