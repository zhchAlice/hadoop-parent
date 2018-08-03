package com.wr.jvm.code.design.DP.backpack;

/**
 * @author: Alice
 * @date: 2018/7/31.
 * @since: 1.0.0
 */

public class BackPack {
    private int wight;
    private int value;

    public BackPack(int wight, int value) {
        this.wight = wight;
        this.value = value;
    }

    public int getWight() {
        return wight;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "{\"BackPack\":{"
                + "\"wight\":\"" + wight + "\""
                + ", \"value\":\"" + value + "\""
                + "}}";
    }
}
