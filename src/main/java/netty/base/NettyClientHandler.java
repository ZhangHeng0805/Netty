package netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends SimpleChannelInboundHandler<String> {
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
////        super.channelActive(ctx);
//        ByteBuf buf= Unpooled.copiedBuffer("客户端上线".getBytes(CharsetUtil.UTF_8));
//        ctx.writeAndFlush(buf);
//    }
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
////        super.channelRead(ctx, msg);
//        ByteBuf buf= (ByteBuf) msg;
//        System.out.println(buf.toString(CharsetUtil.UTF_8));
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s.trim());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
//        cause.printStackTrace();
        if (cause.getMessage().indexOf("远程主机强迫关闭了一个现有的连接")>=0){
            System.out.println("服务器关闭");
        }else {
            System.out.println("服务器错误："+ cause.getMessage());
        }
        ctx.close();
    }
}
