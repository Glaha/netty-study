package discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.util.concurrent.EventExecutorGroup;

public class DiscardClientHandler extends SimpleChannelInboundHandler<Object> {

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
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        content.release();
    }
}
