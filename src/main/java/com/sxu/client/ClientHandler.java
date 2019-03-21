package com.sxu.client;

import com.sxu.common.Middleware;
import com.sxu.common.Model;
import io.netty.channel.ChannelHandlerContext;

public class ClientHandler extends Middleware {

    private Client client;

    public ClientHandler(Client client) {
        super("client");
        this.client = client;
    }

    @Override
    protected void handlerData(ChannelHandlerContext ctx, Object msg) {
        Model model = (Model) msg;
        System.out.println("client  收到数据： " + model.toString());
    }
    @Override
    protected void handlerAllIdle(ChannelHandlerContext ctx) {
        super.handlerAllIdle(ctx);
        sendPingMsg(ctx);
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        client.doConnect();
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.out.println(name + "exception :"+ cause.toString());
    }
}
