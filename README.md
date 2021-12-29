# Netty
# Netty聊天室
## 下载：
+ 项目依赖[netty-all-4.1.50.Final.jar](https://github.com/ZhangHeng0805/Netty/releases/download/V1.0/netty-all-4.1.50.Final.jar)
+ [项目成品jar包](https://github.com/ZhangHeng0805/Netty/releases/download/V1.0/Chat-Server_V1.0.jar)
## 使用方法：下载两个jar包，使用main()方法分别创建服务器和客户端
1. 新建聊天服务器
```java
ChatServer server = new ChatServer(8888); //此处的8888为服务器端口号
server.run(); //启动服务器
```
2. 新建聊天客户端
```java
ChatClient client = new ChatClient("127.0.0.1",8888);  //"127.0.0.1"为服务器的ip地址，8888为服务器的端口号
client.run(); //启动客户端
```
