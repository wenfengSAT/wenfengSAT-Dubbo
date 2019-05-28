package com.dubbo.api.exception.core;

/**
 * @Description： 应用异常
 * @Author： [Guobiao.Li@desay-svautomotive.com] on [2017/8/29]
 * Modified By： [修改人] on [修改日期] for [修改说明]
 */
public class AppException extends BaseException {
    /**
     * 
     */
    private static final long serialVersionUID = 283160129118853963L;

    public AppException(String exceCode) {
        super(exceCode);
    }

    public AppException(String exceCode, String message) {
        super(exceCode, message);
    }

    public AppException(String exceCode, Throwable cause) {
        super(exceCode, cause);
    }

    public AppException(String exceCode, String message, Throwable cause) {
        super(exceCode, message, cause);
    }

    /**
     *
     * @return
     */
    public String toString() {
        StringBuilder sbud = (new StringBuilder("应用异常编码：")).append(this.getExceCode()).append(";");
        return sbud.append(super.toString()).toString();
    }
}
