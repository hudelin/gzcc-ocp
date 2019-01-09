// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BaseResponse.java

package com.hdl.gzccocpcore.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class BaseResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Getter@Setter
    private String code="0";
    @Getter@Setter
    private String msg ="";

    public BaseResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(String msg) {
        this.msg = msg;
    }

    public BaseResponse() {
    }


}
