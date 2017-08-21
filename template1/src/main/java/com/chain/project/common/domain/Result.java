package com.chain.project.common.domain;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 返回的结果
 * <p>
 * 链式编程法，方便级联赋值
 */
public class Result {

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String EMPTY_DATA = "empty data";

    public static final String SUCCESS_CN = "成功";
    public static final String FAILURE_CN = "失败";
    public static final String EMPTY_DATA_CN = "数据为空";

    private Logger logger = LoggerFactory.getLogger(Result.class);
    private boolean success = false;
    private String msg = "";
    private Object data = null;

    /**
     * 对于spring的json转换结果是否需要加密的标识，默认为true即加密
     */
    private Boolean encrypt = true;

    private String[] ignore;

    public Result() {
    }

    public Result(boolean success, String msg, String[] ignore) {
        super();
        this.success = success;
        this.msg = msg;
        this.ignore = ignore;
    }

    public static Result fail() {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(FAILURE);
        return result;
    }

    public static Result fail(String[] ignore) {
        Result result = fail();
        result.setIgnore(ignore);
        return result;
    }

    public static Result fail(String msg) {
        Result result = fail();
        result.setMsg(msg);
        return result;
    }

    public static Result fail(String msg, String[] ignore) {
        Result result = fail();
        result.setMsg(msg);
        result.setIgnore(ignore);
        return result;
    }

    public static Result fail(Object data, String msg) {
        Result result = fail();
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public static Result fail(Object data, String msg, String[] ignore) {
        Result result = fail();
        result.setData(data);
        result.setMsg(msg);
        result.setIgnore(ignore);
        return result;
    }

    public static Result fail(Object data) {
        Result result = fail();
        result.setData(data);
        return result;
    }

    public static Result ok() {
        Result result = new Result();
        result.setSuccess(true);
        result.setMsg(SUCCESS);
        return result;
    }

    public static Result ok(String[] ignore) {
        Result result = ok();
        result.setIgnore(ignore);
        return result;
    }

    public static Result ok(String msg) {
        Result result = ok();
        result.setMsg(msg);
        return result;
    }

    public static Result ok(String msg, String[] ignore) {
        Result result = ok();
        result.setMsg(msg);
        result.setIgnore(ignore);
        return result;
    }

    public static Result ok(Object data, String msg) {
        Result result = ok();
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public static Result ok(Object data, String msg, String[] ignore) {
        Result result = ok();
        result.setData(data);
        result.setMsg(msg);
        result.setIgnore(ignore);
        return result;
    }

    public static Result ok(Object data) {
        Result result = ok();
        result.setData(data);
        return result;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public Result setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String[] getIgnore() {
        return ignore;
    }

    public Result setIgnore(String[] ignore) {
        this.ignore = ignore;
        return this;
    }

    public Boolean isEncrypt() {
        return encrypt;
    }

    public Result setEncrypt(Boolean encrypt) {
        this.encrypt = encrypt;
        return this;
    }


}

