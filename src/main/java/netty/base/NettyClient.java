package netty.base;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class NettyClient {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup(1);
        try {
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline=ch.pipeline();
                            //向pipeline加入解码器
                            pipeline.addLast("decoder",new StringDecoder());
                            //向pipeline加入编码器
                            pipeline.addLast("encoder",new StringEncoder());
                            pipeline.addLast(new NettyClientHandler());
                        }
                    });

            ChannelFuture cf = bootstrap.connect("127.0.0.1", 8888).sync();
            Channel channel = cf.channel();
            System.out.println("=====客户端"+channel.localAddress()+" 启动成功=====");
            //客户端输入信息，创建一个扫描器
            Scanner scanner=new Scanner(System.in);
            while (scanner.hasNextLine()){
                String msg=scanner.nextLine();
                channel.writeAndFlush(msg);
            }
        } catch (Exception e) {
            if (e.getMessage().startsWith("Connection refused")){
                System.out.println("连接拒绝："+e.getMessage());
            }else {
                System.out.println("错误："+e.getMessage());
            }
        } finally {
            group.shutdownGracefully();
        }


    }
}
