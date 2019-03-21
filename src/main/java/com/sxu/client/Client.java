package com.sxu.client;

import com.sxu.common.Model;
import com.sxu.common.MsgPckDecode;
import com.sxu.common.MsgPckEncode;
import com.sxu.common.TypeData;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client {

    private NioEventLoopGroup worker = new NioEventLoopGroup();
    private Channel channel;
    private Bootstrap bootstrap;

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
        client.sendData();
    }

    private void start() {
        bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IdleStateHandler(0,0,10));
                        pipeline.addLast(new MsgPckDecode());
                        pipeline.addLast(new MsgPckEncode());
                        pipeline.addLast(new ClientHandler(Client.this));
                    }
                });
        doConnect();
    }

    protected void doConnect() {

        if (channel != null && channel.isActive()){
            return;
        }
        ChannelFuture connect = bootstrap.connect("127.0.0.1", 8081);
        //实现监听通道连接的方法
        connect.addListener(new ChannelFutureListener() {

            public void operationComplete(ChannelFuture channelFuture) throws Exception {

                if(channelFuture.isSuccess()){
                    channel = channelFuture.channel();
                    System.out.println("连接成功");
                }else{
                    System.out.println("每隔2s重连....");
                    channelFuture.channel().eventLoop().schedule(new Runnable() {
                        public void run() {
                            doConnect();
                        }
                    },2,TimeUnit.SECONDS);
                }
            }
        });
    }
    /**
     * 向服务端发送消息
     */
    private void sendData() {
        Scanner sc= new Scanner(System.in);
        for (int i = 0; i < 1000; i++) {
            if(channel != null && channel.isActive()){
                //获取一个键盘扫描器
                String nextLine = sc.nextLine();
                Model model = new Model();
                model.setType(TypeData.CUSTOMER);
                model.setBody(nextLine);
                channel.writeAndFlush(model);
            }
        }
    }
}
