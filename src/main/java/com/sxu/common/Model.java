package com.sxu.common;

import java.io.Serializable;

public class Model implements Serializable {

    private static final long serialVersionUID = 1L;

    //消息类型
    private int type;

    //消息内容
    private String body;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Model{" +
                "type=" + type +
                ", body='" + body + '\'' +
                '}';
    }
}
