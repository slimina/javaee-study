package qiniu;

import java.io.File;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class QiniuUtil {
	
	private static UploadManager uploadManager = new UploadManager();
	private static final String AK = "9KiG_Y-xxxxxxxxxxxxxxxx";
	private static final String SK = "oTjbZkWxxxxxxxxxxxxxxxxxxxxxxx";
	private static final String BUCKET = "test";
	
	/**
	 * 获取的token有效期问3600s，可以通过
	 * auth.uploadToken(bucket, key, expires, policy)
	 * 设置expires修改有效期，将其缓存下次可以不用重新获取
	 * @return
	 */
	private static String getUpToken() {
		Auth auth = Auth.create(AK, SK);
		return auth.uploadToken(BUCKET);
	}
	
	public static boolean upload(File file,String fileName){
		String token =  getUpToken();
		boolean success = false; 
		try {
			Response response = uploadManager.put(file,fileName, token);
			if(response.isOK()){
				success = true;
			}
		} catch (QiniuException e) {
			Response r = e.response;
			System.err.println(r.toString());
			try {
				System.err.println(r.bodyString());
			} catch (QiniuException e1) {
			}
		}
		return success;
	}

	public static void main(String[] args) {
		File file = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\111.jpg");
		upload(file, "111.jpg");
	}
}
