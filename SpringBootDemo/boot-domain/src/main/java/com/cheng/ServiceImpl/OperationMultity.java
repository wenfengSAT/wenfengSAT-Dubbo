package com.cheng.ServiceImpl;/**
 * @Author: ckj
 * @Description:
 */

import com.cheng.service.Strategy;
import org.springframework.stereotype.Service;

/**
 * 具体策略类二
 *
 * @author ckj
 **/
@Service
public class OperationMultity implements Strategy {
    @Override
    public int doOperation(int num1, int num2) {
        return num1 > num2 ? num1 - num2 : num2 - num1;
    }
}
