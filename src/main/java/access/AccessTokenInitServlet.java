/**  
 * @author wangyi
 * @date 2016-4-7 下午1:40:54 
 * @version V1.0   
 */ 
package access;

import javax.servlet.http.HttpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import base.Constant;

/**定时刷新Access_token
 * 解决的问题：
 * 		1.如何定时刷新access token
 *      2.access token保存在哪里，可以考虑存储在文件、数据库或内存中。具体采用哪种存储方式，需要根据项目实际情况而定。
 * @author wangyi
 * @date 2016-4-7 下午1:40:54 
 */
public class AccessTokenInitServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;   
	
	private static final Logger logger=LoggerFactory.getLogger(AccessTokenInitServlet.class);
	
	public void init(){
		/*
		 * 获取配置参数
		 */
		TokenThread.appid=Constant.APPID;
		
		TokenThread.appsecret =Constant.APPSECRET;
		
		logger.info("weixin api appid:{}", TokenThread.appid);    
		
		logger.info("weixin api appsecret:{}", TokenThread.appsecret); 
		
        if ("".equals(TokenThread.appid) || "".equals(TokenThread.appsecret)) {    
        	 logger.error("appid and appsecret configuration error, please check carefully."); 
        } else {    
            // 启动定时获取access_token的线程    
            new Thread(new TokenThread()).start();    
        }    
		
	}
}
