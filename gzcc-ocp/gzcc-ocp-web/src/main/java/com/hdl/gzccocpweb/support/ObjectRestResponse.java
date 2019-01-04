// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectRestResponse.java

package com.hdl.gzccocpweb.support;


// Referenced classes of package net.shadowedu.metis.common.base.response:
//			BaseResponse

public class ObjectRestResponse extends BaseResponse {

    private static final long serialVersionUID = 1L;
    private Object data;

    public ObjectRestResponse() {
    }

    public ObjectRestResponse(Object data) {
        this.data = data;
    }

    public ObjectRestResponse data(Object data) {
        setData(data);
        return this;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
