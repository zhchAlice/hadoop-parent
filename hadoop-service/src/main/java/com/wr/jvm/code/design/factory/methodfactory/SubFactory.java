package com.wr.jvm.code.design.factory.methodfactory;

import com.wr.jvm.code.design.factory.Operator;
import com.wr.jvm.code.design.factory.OperatorSub;

/**
 * @author: Alice
 * @date: 2018/7/28.
 * @since: 1.0.0
 */
public class SubFactory implements IFactory {
    @Override
    public Operator createOperator() {
        return new OperatorSub();
    }
}
