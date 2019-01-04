// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BaseResponse.java

package com.hdl.gzccocpweb.support;

import java.io.Serializable;

public class BaseResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private int code=0;
    private String msg ="";

    public BaseResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(String msg) {
        this.msg = msg;
    }

    public BaseResponse() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
