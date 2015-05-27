package cn.slimsmart.fastdfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.csource.fastdfs.UploadCallback;

public class UploadFileByStream {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//连接超时的时限，单位为毫秒  
		ClientGlobal.setG_connect_timeout(2000);  
		//网络超时的时限，单位为毫秒  
		ClientGlobal.setG_network_timeout(30000);  
		  
		ClientGlobal.setG_anti_steal_token(false);  
		//字符集  
		ClientGlobal.setG_charset("UTF-8");  
		ClientGlobal.setG_secret_key(null);  
		//HTTP访问服务的端口号    
		ClientGlobal.setG_tracker_http_port(7271);  
		//Tracker服务器列表  
		InetSocketAddress[] tracker_servers = new InetSocketAddress[3];  
		tracker_servers[0] = InetSocketAddress.createUnresolved("192.168.18.178", 22122) ;  
		ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers));  
	}
	
	/** 
	 * Upload File to DFS, directly transferring java.io.InputStream to java.io.OutStream 
	 * @author Poechant 
	 * @email zhongchao.ustc@gmail.com 
	 * @param fileBuff, file to be uploaded. 
	 * @param uploadFileName, the name of the file. 
	 * @param fileLength, the length of the file. 
	 * @return the file ID in DFS. 
	 * @throws IOException  
	 */  
	public String[] uploadFileByStream(InputStream inStream, String uploadFileName, long fileLength) throws IOException {  
	      
	    String[] results = null;  
	    String fileExtName = "";  
	    if (uploadFileName.contains(".")) {  
	        fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);  
	    } else {  
	    	System.out.println("Fail to upload file, because the format of filename is illegal.");  
	        return results;  
	    }  
	      
	    TrackerClient tracker = new TrackerClient();  
	       TrackerServer trackerServer = tracker.getConnection();  
	       StorageServer storageServer = null;  
	       StorageClient1 client = new StorageClient1(trackerServer, storageServer);  
	         
	       NameValuePair[] metaList = new NameValuePair[3];  
	       metaList[0] = new NameValuePair("fileName", uploadFileName);  
	       metaList[1] = new NameValuePair("fileExtName", fileExtName);  
	       metaList[2] = new NameValuePair("fileLength", String.valueOf(fileLength));  
	         
	       try {  
	        // results[0]: groupName, results[1]: remoteFilename.  
	        results = client.upload_file(null, fileLength, new UploadFileSender(inStream), fileExtName, metaList);  
	    } catch (Exception e) {  
	        System.out.println("Upload file \"" + uploadFileName + "\"fails");  
	    }  
	         
	        trackerServer.close();  
	      
	    return results;       
	}  
}

class UploadFileSender implements UploadCallback {  
    
    private InputStream inStream;  
      
    public UploadFileSender(InputStream inStream) {  
        this.inStream = inStream;  
    }  
      
    public int send(OutputStream out) throws IOException {  
        int readBytes;  
        while((readBytes = inStream.read()) > 0) {  
            out.write(readBytes);  
        }  
        return 0;  
    }  
}   
