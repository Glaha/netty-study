package websocket;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.URI;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

public class WebSocketClient {

    public static void main(String[] args) throws Exception{
        //-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9092 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false
        System.out.println("input go and continue:");
        Scanner scanner = new Scanner(System.in);
        String nextLine = scanner.nextLine();
        System.out.println(nextLine);


        EventLoopGroup group=new NioEventLoopGroup();
        Bootstrap boot=new Bootstrap();
        boot.option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .group(group)
                .handler(new LoggingHandler(LogLevel.INFO))
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline p = socketChannel.pipeline();
                        p.addLast(new ChannelHandler[]{new HttpClientCodec(),
                                new HttpObjectAggregator(1024*1024*10)});
                        p.addLast("hookedHandler", new WebSocketClientHandler());
                    }
                });
        URI websocketURI = new URI("ws://127.0.0.1:9090/ws");
        HttpHeaders httpHeaders = new DefaultHttpHeaders();
        //进行握手
        WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(websocketURI, WebSocketVersion.V13, (String)null, true,httpHeaders);
        System.out.println("connect");
        final Channel channel=boot.connect(websocketURI.getHost(),websocketURI.getPort()).sync().channel();
        WebSocketClientHandler handler = (WebSocketClientHandler)channel.pipeline().get("hookedHandler");
        handler.setHandshaker(handshaker);
        handshaker.handshake(channel);
        //阻塞等待是否握手成功
        handler.handshakeFuture().sync();

        ExecutorService executorService = new ThreadPoolExecutor(10,20,0,
                TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(10),new ThreadPoolExecutor.DiscardPolicy());
        ChannelFutureListener channelFutureListener;
        //frame.retain();
        channelFutureListener = new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                //ReceiveCountEnum.INSTANCE.getInstance().add();
                if (channelFuture.isSuccess()) {
                    //System.out.println("text send success:" + random.length);
                    //System.out.println("text send success:" + String.valueOf(ReceiveCountEnum.INSTANCE.getInstance().getCount()));
                } else {
                    System.out.println("text send failed  " + channelFuture.cause().getMessage());
                }
                //channelFuture.sync();
                //frame.release();
            }
        };

        //Random randomno = new Random();
        //byte[] random = RandomBytesUtil.random(1333);
        while (true) {

            Runnable runnable = new Runnable() {
                public void run() {
                    //System.out.println("text send");
                    //Buf.writeBytes(random);
                    //ByteBuf byteBuf = Unpooled.buffer(1333);
                    //byteBuf.writeBytes(random);
                    // create byte array
                    //byte[] nbyte = byteBuf.array();
                    //randomno.nextBytes(nb      //TextWebSocketFrame frame = new TextWebSocketFrame("我是文本");
                    //byte[] random = RandomBytesUtil.random(10000);
                    /*UnpooledByteBufAllocator allocator = new UnpooledByteBufAllocator(false);
                    ByteBuf byteBuf = allocator.heapBuffer(1500);*/
                    //byteyte);byteBuf.retain()
                    ByteBuf buffer = channel.alloc().buffer(1555);
                    BinaryWebSocketFrame frame = new BinaryWebSocketFrame(buffer);
                    //System.out.println("before send:" + buffer.refCnt());

                    try {

                        //channel.writeAndFlush(frame).addListener(channelFutureListener);
                        channel.writeAndFlush(frame).sync();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        //byteBuf.release();
                        //nbyte = null;
                        //byteBuf.release();
                        //frame.release();
                        //frame = null;
                        //channelFutureListener = null;
                        //random = null;
                        //byteBuf = null;
                    }
                }

            };
            //System.out.println("加入线程");
            executorService.submit(runnable);
        }
        //text.start();
        //bina.start();
    }

}
