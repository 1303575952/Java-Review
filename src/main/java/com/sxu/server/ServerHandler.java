package com.sxu.server;

import com.sxu.common.Middleware;
import com.sxu.common.Model;
import com.sxu.common.TypeData;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends Middleware {
    public ServerHandler() {
        super("Server");
    }

    @Override
    protected void handlerData(ChannelHandlerContext ctx, Object msg) {
        Model model  = (Model) msg;
        System.out.println("server 接收数据 ： " +  model.toString());
        model.setType(TypeData.CUSTOMER);
        model.setBody("---------------");
        ctx.channel().writeAndFlush(model);
        System.out.println("server 发送数据： " + model.toString());
    }

    @Override
    protected void handlerReaderIdle(ChannelHandlerContext ctx) {
        super.handlerReaderIdle(ctx);
        System.err.println(" ---- client "+ ctx.channel().remoteAddress().toString() + " reader timeOut, --- close it");
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.err.println(name + "  exception" + cause.toString());
    }
}
