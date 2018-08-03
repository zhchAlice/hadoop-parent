package com.wr.jvm.syn;

import java.util.HashMap;

/**
 * @author: Alice
 * @date: 2018/7/25.
 * @since: 1.0.0
 */
public class Apple {
    private String color;

    public Apple(String color) {
        this.color = color;
    }

    public boolean equals(Object obj) {
        if(obj==null) return false;
        if (!(obj instanceof Apple))
            return false;
        if (obj == this)
            return true;
        return this.color.equals(((Apple) obj).color);
    }

    public int hashCode(){
        return this.color.hashCode();
    }

    public static void main(String[] args) {
        Apple a1 = new Apple("green");
        Apple a2 = new Apple("red");
        System.out.println(a1.hashCode());
        System.out.println(a2.hashCode());

        //hashMap stores apple type and its quantity
        HashMap<Apple, Integer> m = new HashMap<Apple, Integer>();
        m.put(a1, 10);
        m.put(a2, 20);
        System.out.println(m.get(new Apple("green")));
        System.out.println(m.get(new Apple("red")));
    }
}
