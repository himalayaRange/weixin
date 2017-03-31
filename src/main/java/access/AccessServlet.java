/**  
 * @author wangyi
 * @date 2016-3-28 下午5:49:54 
 * @version V1.0   
 */ 
package access;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.DocumentException;
import base.Constant;
import util.AccessTokenUtil;
import util.CheckUtil;
import util.MessageUtil;

/**
 * @author wangyi
 * @date 2016-3-28 下午5:49:54 
 */
public class AccessServlet extends HttpServlet{
	/**
	 * @Description serialVersionUID属性
	 * @author wangyi
	 * @date 2016-3-28 下午6:31:49 
	 */
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String signature=request.getParameter("signature");
		String timestamp=request.getParameter("timestamp");
		String nonce=request.getParameter("nonce");
		String echostr=request.getParameter("echostr");
		
		PrintWriter out=response.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
		out.close();
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		/*
		 * 获取微信服务器端消息
		 */
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		Map<String, String> map;
		PrintWriter out = response.getWriter();
		try {
			map = MessageUtil.xmlToMap(request);
			String ToUserName=map.get("ToUserName");
			String FromUserName=map.get("FromUserName");
			String MsgType=map.get("MsgType");; // text
			String Content=map.get("Content");
			String message=null;
			if(Constant.MESSAGE_TEXT.equals(MsgType)){ //文本
				if("1".equals(Content)){
					message=MessageUtil.initText(FromUserName, ToUserName, MessageUtil.firstMenu());
				}else if("2".equals(Content)){
					message=MessageUtil.initText(FromUserName, ToUserName, MessageUtil.secondMenu());
				}else if("3".equals(Content)){
					message = MessageUtil.initNewsMessage(FromUserName, ToUserName);
				}else if("spring".equals(Content)||"summer".equals(Content)||"autumn".equals(Content)||"winter".equals(Content)){
					String filePath="D:/wearth/"+Content+".png";
					String mediaId=AccessTokenUtil.upload(filePath,TokenThread.accessToken.getAccess_token(), "image");
				    message=MessageUtil.initImageMessage(FromUserName, ToUserName,mediaId);
				}else if("4".equals(Content)){
					String path="D:/music.png";
					String mediaId=AccessTokenUtil.upload(path, TokenThread.accessToken.getAccess_token(), "thumb");
					message = MessageUtil.initMusicMessage(FromUserName, ToUserName,mediaId);
				}else if("5".equals(Content)){
					message=MessageUtil.initText(FromUserName, ToUserName, MessageUtil.threeMenu());
				}else if("?".equals(Content)||"？".equals(Content)){
					message=MessageUtil.initText(FromUserName, ToUserName, MessageUtil.menuText());
				}else{
					if("".equals(Content)){
						message = MessageUtil.initText(FromUserName, ToUserName, MessageUtil.threeMenu());
					}else{
						message = MessageUtil.initText(FromUserName, ToUserName, AccessTokenUtil.translate(Content));
					}
				}
			}else if(Constant.MESSAGE_EVENT.equals(MsgType)){ //事件推送
				String eventType=map.get("Event");
				if(Constant.MESSAGE_SUBSCRIBE.equals(eventType)){ //关注推送
					message=MessageUtil.initText(FromUserName, ToUserName, MessageUtil.menuText());
				}else if(Constant.MESSAGE_CLICK.equals(eventType)){ //点击事件
					message=MessageUtil.initText(FromUserName, ToUserName, MessageUtil.menuText());
				}else if(Constant.MESSAGE_VIEW.equals(MsgType)){ //view事件
					String url=map.get("EventKey");
					message=MessageUtil.initText(FromUserName, ToUserName, url);
				}else if(Constant.MESSAGE_SCANCODE.equals(eventType)){ //扫码事件一般不会发送消息，而是会进行相关的业务逻辑处理
					String key = map.get("EventKey");
					message = MessageUtil.initText(FromUserName,ToUserName, key);
				}else if(Constant.MESSAGE_PIC_PHOTO_OR_ALBUM.equals(eventType)){
					String EventKey=map.get("EventKey");
					message = MessageUtil.initText(FromUserName,ToUserName, EventKey);
				}
			}else if(Constant.MESSAGE_LOCATION.equals(MsgType)){ //位置信息
					String label=map.get("Label");
					message = MessageUtil.initText(FromUserName,ToUserName, label);
			}
			
			System.out.println(message);
			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
		} catch (NoSuchAlgorithmException e) {
		} catch (NoSuchProviderException e) {
		}finally{
			out.close();
		}
	}
}
