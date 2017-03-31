/**  
 * @author wangyi
 * @date 2016-4-6 下午2:12:54 
 * @version V1.0   
 */ 
package test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import entity.AccessToken;
import net.sf.json.JSONObject;
import util.AccessTokenUtil;

/**
 * @author wangyi
 * @date 2016-4-6 下午2:12:54 
 */
public class AccessTokenTest {
	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
		try {
			AccessToken token=AccessTokenUtil.getAccessToken();
			System.out.println("票据："+token.getAccess_token());
			System.out.println("有效时间："+token.getExpires_in());
			/*
			 * 上传
			 * 
			 */
			/*String path="D:/upload.png";
			String mediaId=AccessTokenUtil.upload(path, token.getAccess_token(), "image");*/
			/*String path="D:/music.png";
			String mediaId=AccessTokenUtil.upload(path, token.getAccess_token(), "thumb");
			System.out.println(mediaId);*/
			/*String menu= JSONObject.fromObject(AccessTokenUtil.initMenu()).toString();
			int result=AccessTokenUtil.createMenu(menu,token.getAccess_token());
			if(result==0){
				System.out.println("菜单创建成功");
			}else{
				System.out.println("菜单创建失败");
			}*/
			
			/*
			 * 菜单查询
			 */
			JSONObject jsonObject = AccessTokenUtil.queryMenu(token.getAccess_token());
			System.out.println(jsonObject);
		}
		 catch (IOException e) {
			e.printStackTrace();
		 }
	}
}
