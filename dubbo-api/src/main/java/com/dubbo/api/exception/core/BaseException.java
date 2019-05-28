package com.dubbo.api.exception.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description： 异常收集抽象父类
 * @Author： [Guobiao.Li@desay-svautomotive.com] on [2017/8/29]
 * Modified By： [修改人] on [修改日期] for [修改说明]
 */
public abstract class BaseException extends Exception {

    private static final long serialVersionUID = 7383380871083031654L;

    /**
     * 异常信息编码
     */
    private String exceCode;

    /**
     * 异常信息
     */
    private Map<String, Object> exceParams;

    BaseException(String exceCode) {
        this.exceCode = exceCode;
    }

    BaseException(String exceCode, String message) {
        super(message);
        this.exceCode = exceCode;
    }

    BaseException(String exceCode, Throwable cause) {
        super(cause);
        this.exceCode = exceCode;
    }

    BaseException(String exceCode, String message, Throwable cause) {
        super(message, cause);
        this.exceCode = exceCode;
    }

    public String getExceCode() {
        return this.exceCode;
    }

    public void setExceCode(String exceCode) {
        this.exceCode = exceCode;
    }

    /**
     *
     * @param key key
     * @param value value
     * @return
     */
    public BaseException put(String key, Object value) {
        return this.checkAndPut(key, value);
    }

    /**
     *
     * @param key key
     * @param value value
     * @return
     */
    private BaseException checkAndPut(String key, Object value) {
        if (StringUtils.isEmpty(key)) {
            return this;
        } else {
            if (this.exceParams == null) {
                this.exceParams = new HashMap<>();
            }

            this.exceParams.put(key, value);
            return this;
        }
    }

    /**
     * 
     * @param params params
     * @return
     */
    public BaseException putAll(Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            if (this.exceParams == null) {
                this.exceParams = new HashMap<>();
            }

            this.exceParams.putAll(params);
            return this;
        } else {
            return this;
        }
    }

    public Map<String, Object> getAll() {
        return this.exceParams;
    }
}
