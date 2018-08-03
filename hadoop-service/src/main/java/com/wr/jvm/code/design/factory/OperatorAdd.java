package com.wr.jvm.code.design.factory;

/**
 * @author: Alice
 * @date: 2018/7/28.
 * @since: 1.0.0
 */
public class OperatorAdd extends Operator {
    @Override
    public double getResult() {
        return getVal1() + getVal2();
    }
}
