package cn.slimsmart.xmpp.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class XmppChatText {

	private static Connection connection = null;

	public static void main(String[] args) throws XMPPException, Exception {
		SmackConfiguration.setKeepAliveInterval(3000);
		SmackConfiguration.setPacketReplyTimeout(6000);
		connection = getConnection();
		connection.connect();// 开启连接
		Thread.sleep(10000);
		connection.login("test", "123456");// 利用用户名和密码登录
		SendBCMsg("广播消息","admin");
		SendMsg("您好吗");
		System.out.println(getRosterList());
		System.out.println(getRoster());
		while(true);
	}

	/**
	 * 获取连接
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		// 新建连接配置对象，设置服务器IP和监听端口
		ConnectionConfiguration connectionConfig = new ConnectionConfiguration("192.168.18.67", 5222);
		// 允许自动连接
		connectionConfig.setReconnectionAllowed(true);
		connectionConfig.setSendPresence(true);
		/** 是否启用压缩 */
		connectionConfig.setCompressionEnabled(true);
		/** 是否启用安全验证 */
		connectionConfig.setSASLAuthenticationEnabled(true);
		/** 是否启用调试 */
		connectionConfig.setDebuggerEnabled(false);
		Connection connection = new XMPPConnection(connectionConfig);
		return connection;
	}

	/**
	 * 销毁连接
	 * 
	 * @param connection
	 */
	public static void destory() {
		if (connection != null) {
			connection.disconnect();
			connection = null;
		}
	}

	/**
	 * 发送广播消息
	 */
	public static void SendBCMsg(String messgage, String groupName) throws XMPPException {
		Message m = new Message();
		m.setBody(messgage);// 设置消息。
		m.setTo(groupName + "@xmpp");// [groupname]@[serviceName].[serverName]
		connection.sendPacket(m);
	}

	/**
	 * 发送即使消息
	 * @param username
	 * @param pass
	 * @param messgage
	 * @throws XMPPException
	 */
	public static void SendMsg(String messgage) throws XMPPException {
		Chat mychat = connection.getChatManager().createChat("admin@xmpp", // 接收端的JID，JID是要加域的
				new MessageListener() {
					@Override
					public void processMessage(Chat chat, Message message) {
						String messageBody = message.getBody();
						System.out.println("收到信息：" + messageBody + " " + message.getFrom());
					}
				});
		mychat.sendMessage(messgage); // 发送信息
	}

    /** 
     * 获取好友列表 
     *  
     * @param username 
     * @param pass 
     * @return 
     * @throws XMPPException 
     */  
    public static List<RosterEntry> getRosterList()  
            throws XMPPException {  
        Collection<RosterEntry> rosters = connection.getRoster().getEntries();  
        for (RosterEntry rosterEntry : rosters) {  
            System.out.print("name: " + rosterEntry.getName() + ",jid: "  
                    + rosterEntry.getUser()); // 此处可获取用户JID  
            System.out.println();  
        }  
        return null;  
    }  
	
    /** 
     * 获取用户列表（含组信息） 
     *  
     * @param username 
     * @param pass 
     * @return 
     * @throws XMPPException 
     */  
    public static List<RosterEntry> getRoster()  
            throws XMPPException {  
        Roster roster = connection.getRoster();  
        List<RosterEntry> EntriesList = new ArrayList<RosterEntry>();  
        Collection<RosterEntry> rosterEntry = roster.getEntries();  
        Iterator<RosterEntry> i = rosterEntry.iterator();  
        while (i.hasNext()) {  
            EntriesList.add(i.next());  
        }  
        return EntriesList;  
    }  
}
