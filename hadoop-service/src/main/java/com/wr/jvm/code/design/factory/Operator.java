package com.wr.jvm.code.design.factory;

/**
 * @author: Alice
 * @date: 2018/7/28.
 * @since: 1.0.0
 */
public abstract class Operator {
    private double val1;
    private double val2;

    public double getVal1() {
        return val1;
    }

    public void setVal1(double val1) {
        this.val1 = val1;
    }

    public double getVal2() {
        return val2;
    }

    public void setVal2(double val2) {
        this.val2 = val2;
    }

    public abstract double getResult();
}
