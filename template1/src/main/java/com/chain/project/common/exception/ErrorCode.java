package com.chain.project.common.exception;

public class ErrorCode {

    //默认错误
    public static final int DEFAULT = 10000;

    //测试事务时随机产生的错误
    public static final int RANDOM_DISASTER = 10010;

    //客户端错误
    public static final int CLIENT = 10011;

    //服务器内部错误
    public static final int SERVER = 10012;

    //客户端传入参数错误
    public static final int PARAM_IN = 10013;

    //业务错误
    public static final int BUSINESS = 10014;

    //事务回滚
    public static final int ROLL_BACK = 10015;

}
