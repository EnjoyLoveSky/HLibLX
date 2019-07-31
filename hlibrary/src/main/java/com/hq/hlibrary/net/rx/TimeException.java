package com.hq.hlibrary.net.rx;

/**
 * 自定义错误信息，统一处理返回处理
 */
public class TimeException extends RuntimeException {

    public static final int NO_DATA = 0x2;

    public TimeException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public TimeException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 转换错误数据
     *
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code) {
        String message = "";
        switch (code) {
            case NO_DATA:
                message = "无数据";
                break;
            default:
                message = "error";
                break;

        }
        return message;
    }
}

