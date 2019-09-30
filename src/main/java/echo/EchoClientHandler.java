package echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private ByteBuf content;
    private ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;

        content = ctx.alloc().directBuffer(100).writeZero(100);

        generateTraffic();
    }

    private void generateTraffic() {
        ctx.writeAndFlush(content.retainedDuplicate()).addListener(trafficGenerator);
    }

    private ChannelFutureListener trafficGenerator = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            if (channelFuture.isSuccess()) {
                //generateTraffic();
            } else {
                channelFuture.cause().printStackTrace();
                channelFuture.channel().close();
            }
        }
    };

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
    }
}
