package com.sxu.common;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public abstract class Middleware extends ChannelInboundHandlerAdapter {
    protected String name;
    //记录次数
    private int heartbeatCount = 0;

    public Middleware(String name) {
        this.name = name;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Model m = (Model) msg;
        int type = m.getType();
        switch (type) {
            case 1:
                sendPongMsg(ctx);
                break;
            case 2:
                System.out.println(name + " get  pong  msg  from" + ctx.channel().remoteAddress());
                break;
            case 3:
                handlerData(ctx, msg);
                break;
            default:
                break;
        }
    }

    protected abstract void handlerData(ChannelHandlerContext ctx, Object msg);

    protected void sendPingMsg(ChannelHandlerContext ctx) {
        Model model = new Model();

        model.setType(TypeData.PING);

        ctx.channel().writeAndFlush(model);

        heartbeatCount++;

        System.out.println(name + " send ping msg to " + ctx.channel().remoteAddress() + "count :" + heartbeatCount);
    }

    private void sendPongMsg(ChannelHandlerContext ctx) {

        Model model = new Model();

        model.setType(TypeData.PONG);

        ctx.channel().writeAndFlush(model);

        heartbeatCount++;

        System.out.println(name + " send pong msg to " + ctx.channel().remoteAddress() + " , count :" + heartbeatCount);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        IdleStateEvent stateEvent = (IdleStateEvent) evt;

        switch (stateEvent.state()) {
            case READER_IDLE:
                handlerReaderIdle(ctx);
                break;
            case WRITER_IDLE:
                handlerWriterIdle(ctx);
                break;
            case ALL_IDLE:
                handlerAllIdle(ctx);
                break;
            default:
                break;
        }
    }

    protected void handlerAllIdle(ChannelHandlerContext ctx) {
        System.err.println("---ALL_IDLE---");
    }

    protected void handlerWriterIdle(ChannelHandlerContext ctx) {
        System.err.println("---WRITER_IDLE---");
    }


    protected void handlerReaderIdle(ChannelHandlerContext ctx) {
        System.err.println("---READER_IDLE---");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println(" ---" + ctx.channel().remoteAddress() + "----- is  action");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.println(" ---" + ctx.channel().remoteAddress() + "----- is  inAction");
    }
}
