package com.dubbo.api.exception.core;

/**
 * 
 * @Description： 业务异常
 * @author [ Wenfeng.Huang ] on [2019年6月3日下午7:50:44]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public class BusiException extends BaseException {
    private static final long serialVersionUID = -6716891036272026286L;

    public BusiException(String exceCode) {
        super(exceCode);
    }

    public BusiException(String exceCode, String message) {
        super(exceCode, message);
    }

    public BusiException(String exceCode, Throwable cause) {
        super(exceCode, cause);
    }

    public BusiException(String exceCode, String message, Throwable cause) {
        super(exceCode, message, cause);
    }

    /**
     *
     * @return
     */
    public String toString() {
        StringBuilder sbud = new StringBuilder("业务异常编码：").append(getExceCode()).append(";");
        return sbud.toString();
    }
}
