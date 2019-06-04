package com.cheng.ServiceImpl;

import com.cheng.service.Strategy;
import org.springframework.stereotype.Service;

/**
 * 具体策略类一
 *
 * @author ckj
 **/
@Service
public class OperationAdd implements Strategy {
    @Override
    public int doOperation(int num1, int num2) {
        return num1 + num2;
    }
}
