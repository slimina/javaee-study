package cn.slimsmart.nio.demo.mini;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReaderHandle implements Reactor {

	private static final Logger log = LoggerFactory.getLogger(ReaderHandle.class);
	private byte[] bytes = new byte[0];
	private ExecutorService executor;

	public ReaderHandle(ExecutorService executor) {
		this.executor = executor;
	}

	@Override
	public void execute(SelectionKey key) {
		SocketChannel sc = (SocketChannel) key.channel();
		try {
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			int len = -1;
			while (sc.isConnected() && (len = sc.read(buffer)) > 0) {
				buffer.flip();
				byte[] content = new byte[buffer.limit()];
				buffer.get(content);
				bytes =content;
				buffer.clear();
			}
			if (len == 0) {
				key.interestOps(SelectionKey.OP_READ);
				key.selector().wakeup();
			} else if (len == -1) {
				Callable<byte[]> call = new ProcessCallable(bytes);
				Future<byte[]> task = executor.submit(call);
				ByteBuffer output = ByteBuffer.wrap(task.get());
				sc.register(key.selector(), SelectionKey.OP_WRITE, new WriterHandle(output));
			}
		} catch (Exception e) {
			log.debug("e:{}",e);
		}
	}

}
