package cn.slimsmart.nio.demo.mini;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriterHandle implements Reactor {

	private static final Logger log = LoggerFactory.getLogger(WriterHandle.class);
	private ByteBuffer output;

	public WriterHandle(ByteBuffer output) {
		this.output = output;
	}

	@Override
	public void execute(SelectionKey key) {
		SocketChannel sc = (SocketChannel) key.channel();
		try {
			while (sc.isConnected() && output.hasRemaining()) {
				int len = sc.write(output);
				if (len < 0) {
					throw new EOFException();
				}
				if (len == 0) {
					key.interestOps(SelectionKey.OP_WRITE);
					key.selector().wakeup();
					break;
				}
			}
			if (!output.hasRemaining()) {
				output.clear();
				key.cancel();
				sc.close();
			}
		} catch (IOException e) {
			log.debug("e:{}", e);
		}
	}
}
