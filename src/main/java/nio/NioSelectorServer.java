package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioSelectorServer {

    public static void main(String[] args) throws IOException {

        //创建NIO ServerSocketChannel
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        //监听端口
        socketChannel.socket().bind(new InetSocketAddress(8888));
        //设置非阻塞
        socketChannel.configureBlocking(false);
        Selector selector=Selector.open();
        SelectionKey selectionKey=socketChannel.register(selector,SelectionKey.OP_ACCEPT);
        System.out.println("服务器启动了！");
        while (true){
            //阻塞等待余姚处理的时间发生
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                //如果是OP_ACCEPT事件，则进行连接和事件注册
                if (key.isAcceptable()){
                    ServerSocketChannel server= (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel1=server.accept();
                    socketChannel1.configureBlocking(false);
                    SelectionKey selKey=socketChannel1.register(selector,SelectionKey.OP_READ,SelectionKey.OP_WRITE);
                    System.out.println("客户端连接成功！");
                }else if (key.isReadable()){//如果是OP_READ事件，则进行读取和打印
                    SocketChannel socketChannel1= (SocketChannel) key.channel();
                    ByteBuffer allocate = ByteBuffer.allocate(128);
                    int read = socketChannel1.read(allocate);
                    //如果有数据，把数据打印出来
                    if (read>0){
                        System.out.println("接收消息："+new String(allocate.array()));
                    }else if (read==-1){//如果客户端断开连接，关闭Socket
                            System.out.println("客户端断开连接");
                            socketChannel1.close();
                    }
                }else if (key.isWritable()){
                    SocketChannel channel = (SocketChannel) key.channel();
                }
                //从事件集合删除本次处理的key，防止下次select重复处理
                iterator.remove();
            }
        }
    }
}
