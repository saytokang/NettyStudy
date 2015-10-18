package netty.study;

import java.nio.ByteOrder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TelnetServer {
	public static void main(String[] args) throws InterruptedException {
		new TelnetServer().run();
	}

	private int PORT = 8000;

	private void run() throws InterruptedException {
		// TODO Auto-generated method stub
		EventLoopGroup master = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(master, worker)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						// TODO Auto-generated method stub
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new LoggingHandler(LogLevel.INFO));
						pipeline.addLast(new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN,1024,0,4,0,4, false));
						pipeline.addLast(new StringDecoder());
//						pipeline.addLast(new StringEncoder());
						pipeline.addLast(new ServerMsgHandler());
					}
					
				});
			b.bind(PORT).sync().channel().closeFuture().sync();
		} finally {
			// TODO: handle exception
			master.shutdownGracefully();
			worker.shutdownGracefully();
		}
		
	}
}
