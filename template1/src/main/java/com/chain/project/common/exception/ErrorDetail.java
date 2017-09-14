package com.chain.project.common.exception;

/**
 * 错误详情
 */
public class ErrorDetail {

    private int code;
    private String msg;

    public ErrorDetail() {
        this.code = ErrorCode.DEFAULT;
        this.msg = ErrorCode.getErrorMsg(code);
    }

    public ErrorDetail(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ErrorDetail(int code) {
        this.code = code;
        this.msg = ErrorCode.getErrorMsg(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ErrorDetail{");
        sb.append("code=").append(code);
        sb.append(", msg='").append(msg).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
