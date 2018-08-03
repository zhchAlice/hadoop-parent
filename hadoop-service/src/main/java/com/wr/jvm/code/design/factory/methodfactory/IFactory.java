package com.wr.jvm.code.design.factory.methodfactory;

import com.wr.jvm.code.design.factory.Operator;

/**
 * @author: Alice
 * @date: 2018/7/28.
 * @since: 1.0.0
 */
public interface IFactory {
    Operator createOperator();
}
