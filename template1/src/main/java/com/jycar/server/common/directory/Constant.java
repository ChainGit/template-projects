package com.jycar.server.common.directory;

/**
 * 字典类
 */
public class Constant {


    //用于拦截器
    public static final String JSON_MAP = "json_map";
    public static final String REQUEST_ENCRYPT_JSON_KEY = "s";
    public static final String REQUEST_PLAIN_JSON_KEY = "ns";
    public static final boolean RESPONSE_ENCRYPT_JSON_KEY = true;
    public static final boolean RESPONSE_PLAIN_JSON_KEY = false;

    //用于返回值
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String NULL = "null";

    //用于分页
    public static final String CURRENT_PAGE = "page";
    public static final String EACH_PAGE_ROWS = "rows";
    public static final String EACH_PAGE_RECORDS = "each";
    public static final String TOTAL_RECORDS = "records";
    public static final String TOTAL_PAGES = "total";

}
