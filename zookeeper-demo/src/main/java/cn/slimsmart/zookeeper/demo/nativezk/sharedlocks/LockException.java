package cn.slimsmart.zookeeper.demo.nativezk.sharedlocks;

public class LockException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private Exception ex;
	
	public LockException(Exception ex){
		this.ex = ex;
	}
	
	public void printStackTrace() {
		ex.printStackTrace();
	}
}

