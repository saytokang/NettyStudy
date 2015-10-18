package netty.study;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.ByteBuffer;

public class ClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		byte[] bsize = null;
		System.out.println("connected....");
		ByteBuf buf = Unpooled.buffer();
		try {
			String msg1 = "한글abcdefghjklmnopqrstuvwxyz111";
			bsize = ByteBuffer.allocate(4).putInt(msg1.length()).array();
			buf.writeBytes(concatByte(bsize, msg1.getBytes()));
			ctx.writeAndFlush(buf);
			
			System.out.println("len:" + ByteBuffer.wrap(bsize).getInt());
			System.out.println("sending:" +new String(concatByte(bsize, msg1.getBytes())));
		} finally {
			buf.release();
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
	}
	
	public byte[] concatByte(byte[] b1, byte[] b2) {
		byte[] newBuff = new byte[b1.length + b2.length];
		System.arraycopy(b1, 0, newBuff, 0, b1.length);
		System.arraycopy(b2, 0, newBuff, b1.length, b2.length);
		return newBuff;
	}
}
