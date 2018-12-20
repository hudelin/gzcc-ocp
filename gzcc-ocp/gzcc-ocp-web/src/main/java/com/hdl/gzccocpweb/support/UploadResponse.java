package com.hdl.gzccocpweb.support;

public class UploadResponse {

    private String code="0";

    private String msg="";

    private Data data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public UploadResponse(String src) {
        this.data = new Data(src);
    }

    class Data{
        private String src;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public Data(String src) {
            this.src = src;
        }
    }

}
