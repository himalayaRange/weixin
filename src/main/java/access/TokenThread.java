/**  
 * @author wangyi
 * @date 2016-4-7 下午1:57:14 
 * @version V1.0   
 */ 
package access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.AccessTokenUtil;
import entity.AccessToken;

/**
 * @author wangyi
 * @date 2016-4-7 下午1:57:14 
 */
public class TokenThread implements Runnable{
	
	private static Logger logger = LoggerFactory.getLogger(TokenThread.class);    
	
	 // 第三方用户唯一凭证    
    public static String appid = "";    
    
    // 第三方用户唯一凭证密钥    
    public static String appsecret = "";    
    
    public static AccessToken accessToken = null;   
	@Override
	public void run() {
		while(true){
			 try {
				accessToken = AccessTokenUtil.getAccessToken();
				if(accessToken!=null){
					 logger.info("获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpires_in(), accessToken.getAccess_token());  
					// 休眠7000秒    
						Thread.sleep((accessToken.getExpires_in() - 200) * 1000);
				}else{
					// 如果access_token为null，60秒后再获取    
                    Thread.sleep(60 * 1000);   
				}
			} catch (Exception e) {
				 logger.error("{}", e);    
			}
		}
	}

}
