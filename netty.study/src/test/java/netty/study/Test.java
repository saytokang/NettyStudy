package netty.study;

import java.nio.ByteBuffer;


public class Test {

//	@org.junit.Test
	public void test() {
		int len = 4;
		String slen = String.format("%4s", len).replace(' ', '0');
		System.out.println(slen);
		byte[] b = slen.getBytes();
//		int result = ByteBuffer.wrap(b).getInt();
		System.out.println(b.length);
	}

	@org.junit.Test
	public void test2() {
		int len = 5;
//		byte[] buff = (len+"").getBytes();
		byte[] buff = ByteBuffer.allocate(4).putInt(len).array();
		System.out.println("len: " +buff.length);
		System.out.println(ByteBuffer.wrap(buff).getInt());
	}
	
}
