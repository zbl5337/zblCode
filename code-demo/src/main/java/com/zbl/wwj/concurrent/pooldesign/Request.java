package com.zbl.wwj.concurrent.pooldesign;

/**
 * Create By : FanXiaoYun
 * Date      : 2019-10-23
 * Describe  : simulate Request
 *
 * @author wyk
 */
public class Request {

    final private String value;

    public Request(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
