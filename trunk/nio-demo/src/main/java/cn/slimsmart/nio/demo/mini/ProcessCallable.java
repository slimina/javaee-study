package cn.slimsmart.nio.demo.mini;

import java.util.concurrent.Callable;

public class ProcessCallable implements Callable<byte[]> {

	private byte[] bytes = null;

	public ProcessCallable(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public byte[] call() throws Exception {
		return bytes;
	}

}
