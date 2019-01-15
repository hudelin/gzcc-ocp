// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectRestResponse.java

package com.hdl.gzccocpcore.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
public class ObjectRestResponse extends BaseResponse {

    private static final long serialVersionUID = 1L;

    @Getter@Setter
    private Object data;

    public ObjectRestResponse(Object data) {
        this.data = data;
    }

    public ObjectRestResponse data(Object data) {
        setData(data);
        return this;
    }

}
