package com.sxu.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * 编码器
 */
public class MsgPckEncode extends MessageToByteEncoder<Object> {
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf buf) throws Exception {
        MessagePack pack = new MessagePack();
        byte[] write = pack.write(msg);
        buf.writeBytes(write);
    }
}
