package cn.smartslim.mqtt.demo.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.sun.xml.internal.ws.util.StringUtils;

//采用HTTP DELETE方式的订阅相关的MQTT的主题的信息
public class HttpDelete {
	private static final String DEFAULT_HOST = "192.168.13.55";
	private static final String DEFAULT_PORT = "8080";
	private static final String DEFAULT_QUEUE = "mqtt/http/delete";

	private static final String DEFAULT_CONTEXT_ROOT = "mq";
	public static String newline = System.getProperty("line.separator");
	private static final String MESSAGE_BOUNDARY = "_________________________________________________________________________________________";

	// the maximum length of the message that we want to print to the screen
	private static final int MAX_OUTPUT_MESSAGE_SIZE = 256;

	private static int OK_RC = 200;

	/**
	 * 构建订阅主题队列路径
	 * 
	 * @param host
	 * @param port
	 * @param context
	 * @param queueName
	 */
	private static String getPublishQueueURL(String host, String port, String context, String queueName) {
		StringBuffer urlString = new StringBuffer("http://");
		if (host ==null || host.equals("")) {
			host = DEFAULT_HOST;
		}

		if (port ==null || port.equals("")) {
			port = DEFAULT_PORT;
		}
		urlString.append(host).append(":").append(port);
		if (context ==null || context.equals("")) {
			context = DEFAULT_CONTEXT_ROOT;
		}
		urlString.append("/");
		urlString.append(context);
		urlString.append("/msg/queue/");
		if (queueName ==null || queueName.equals("")) {
			queueName = DEFAULT_QUEUE;
		}
		urlString.append(queueName);
		System.out.println("urlString=" + urlString);
		return urlString.toString();
	}

	/**
	 * 通过HTTP POST 订阅主题的具体实现
	 * 
	 * @param host
	 * @param port
	 * @param context
	 * @param queueName
	 * @return
	 * @throws MalformedURLException
	 */
	public static boolean subTopic(String host, String port, String context, String queueName) {
		String publishURL = getPublishQueueURL(host, port, context, queueName);
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(publishURL);
			connection = (HttpURLConnection) url.openConnection();
			/* Build the headers */
			// the verb first.
			connection.setRequestMethod("DELETE");

			// write out what headers we want back
			// the header names are case-sensitive
			connection.setRequestProperty("x-msg-require-headers", "timestamp, expiry, persistence");

			// Now actually send the request message. There is no content as
			// this is a
			// DELETE
			connection.connect();
			String formattedMessage = null;
			// check the response for errors
			int responseCode = connection.getResponseCode();
			if (responseCode == OK_RC) {
				// Get the headers first
				String timestamp = connection.getHeaderField("x-msg-timestamp");
				String expiry = connection.getHeaderField("x-msg-expiry");
				String persistence = connection.getHeaderField("x-msg-persistence");
				// now get the message data
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line = null;
				StringBuffer messageBuffer = new StringBuffer();
				while ((line = reader.readLine()) != null) {
					messageBuffer.append(line);
				}
				String messageBody = messageBuffer.toString();

				formattedMessage = MESSAGE_BOUNDARY + newline;
				// Which is greater the max output message size or the message
				// length?
				int messageSizeToPrint = messageBody.length() > MAX_OUTPUT_MESSAGE_SIZE ? MAX_OUTPUT_MESSAGE_SIZE : messageBody.length();
				formattedMessage += messageBody.substring(0, messageSizeToPrint) + newline;
				formattedMessage += "timestamp = " + timestamp + newline;
				formattedMessage += "expiry = " + expiry + newline;
				formattedMessage += "persistence = " + persistence + newline;

				System.out.println("formattedMessage " + formattedMessage);
			} else {
				String responseMessage = connection.getResponseMessage();
				System.out.println("responsere sponseCode " + responseCode + " response request =" + responseMessage);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return false;
	}

	public static void main(String[] args) {
		HttpDelete.subTopic("192.168.13.55", "8080", "", "mqtt/http/delete");
	}

}
