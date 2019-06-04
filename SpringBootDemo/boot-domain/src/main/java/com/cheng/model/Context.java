package com.cheng.model;

import com.cheng.ServiceImpl.OperationAdd;
import com.cheng.ServiceImpl.OperationMultity;
import com.cheng.service.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 环境角色类
 *
 * @author ckj
 **/
@Component
public class Context {


    private Strategy strategy;

    @Autowired
    private OperationAdd operationAdd;

    @Autowired
    private OperationMultity operationMultity;


    /**
     * 先加后减 算法封装
     */
    public int getResult(int num1, int num2, boolean result) {

        if (result) {
            this.strategy = operationAdd;
            return strategy.doOperation(num1, num2);
        } else {
            this.strategy = operationMultity;
            return strategy.doOperation(num1, num2);
        }
    }
}
