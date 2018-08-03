package com.wr.jvm.code.design.factory;

/**
 * @author: Alice
 * @date: 2018/7/28.
 * @since: 1.0.0
 */
public class OperatorFactory {
    public static Operator createOperator(String op){
        Operator operator = null;
        switch (op){
            case "+":
                operator = new OperatorAdd();
                break;
            case "-":
                operator = new OperatorSub();
                break;
                default:
                    throw new UnsupportedOperationException("error");
        }
        return operator;
    }
}
