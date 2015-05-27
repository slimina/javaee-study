package cn.slimsmart.fastdfs;

import java.io.IOException;
import java.io.InputStream;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;


/*
 * https://github.com/happyfish100/fastdfs-client-java
https://github.com/baoming/FastdfsClient
 * 
 */
public class UploadFileByByte {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String configFileName = "fdfs_client.conf";  
        try {  
            ClientGlobal.init(configFileName);  
        }catch(Exception e){
        	e.printStackTrace();
        }
	}

	/**
	 * Upload File to DFS.
	 * 
	 * @param fileBuff
	 *            , file to be uploaded.
	 * @param uploadFileName
	 *            , the name of the file.
	 * @param fileLength
	 *            , the length of the file.
	 * @return the file ID in DFS.
	 * @throws IOException
	 */
	public String uploadFile(InputStream inStream, String uploadFileName, long fileLength) throws IOException {
		byte[] fileBuff = getFileBuffer(inStream, fileLength);
		String fileId = "";
		String fileExtName = "";
		if (uploadFileName.contains(".")) {
			fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
		} else {
			System.out.println("Fail to upload file, because the format of filename is illegal.");
			return fileId;
		}

		// 建立连接
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection();
		StorageServer storageServer = null;
		StorageClient1 client = new StorageClient1(trackerServer, storageServer);

		// 设置元信息
		NameValuePair[] metaList = new NameValuePair[3];
		metaList[0] = new NameValuePair("fileName", uploadFileName);
		metaList[1] = new NameValuePair("fileExtName", fileExtName);
		metaList[2] = new NameValuePair("fileLength", String.valueOf(fileLength));

		// 上传文件
		try {
			fileId = client.upload_file1(fileBuff, fileExtName, metaList);
		} catch (Exception e) {
			System.out.println("Upload file \"" + uploadFileName + "\"fails");
		}
		trackerServer.close();
		return fileId;
	}

	/**
	 * Transfer java.io.InpuStream to byte array.
	 * 
	 * @param inStream
	 *            , input stream of the uploaded file.
	 * @param fileLength
	 *            , the length of the file.
	 * @return the byte array transferred from java.io.Inputstream.
	 * @throws IOException
	 *             occurred by the method read(byte[]) of java.io.InputStream.
	 */
	private byte[] getFileBuffer(InputStream inStream, long fileLength) throws IOException {

		byte[] buffer = new byte[256 * 1024];
		byte[] fileBuffer = new byte[(int) fileLength];

		int count = 0;
		int length = 0;

		while ((length = inStream.read(buffer)) != -1) {
			for (int i = 0; i < length; ++i) {
				fileBuffer[count + i] = buffer[i];
			}
			count += length;
		}
		return fileBuffer;
	}
}
