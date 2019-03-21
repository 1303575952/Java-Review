package com.sxu.common;

public interface TypeData {
    //client发送心跳
    byte PING = 1;
    //server发送心跳
    byte PONG = 2;
    byte CUSTOMER = 3;
}
