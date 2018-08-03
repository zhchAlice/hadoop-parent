package com.wr.jvm.code.design.factory;

import com.wr.jvm.code.design.factory.methodfactory.AddFactory;
import com.wr.jvm.code.design.factory.methodfactory.IFactory;

/**
 * @author: Alice
 * @date: 2018/7/28.
 * @since: 1.0.0
 */
public class Main {
    public static void main(String[] args) {
        OperatorAdd operatorAdd = new OperatorAdd();
        operatorAdd.setVal1(10);
        operatorAdd.setVal2(20);
        System.out.println(operatorAdd.getResult());

        Operator operator = OperatorFactory.createOperator("+");
        operator.setVal1(1);
        operator.setVal2(2);
        System.out.println(operator.getResult());

        IFactory factory = new AddFactory();
        operator = factory.createOperator();
        operator.setVal1(3);
        operator.setVal2(4);
        System.out.println(operator.getResult());
    }
}
