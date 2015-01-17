package cn.slimsmart.nio.demo.mini;

import java.nio.channels.SelectionKey;

public interface Reactor {
	void execute(SelectionKey key);
}
