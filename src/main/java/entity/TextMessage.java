/**  
 * @author wangyi
 * @date 2016-3-29 上午11:51:31 
 * @version V1.0   
 */
package entity;

/**
 * 	文本消息对象
 * 
 * @author wangyi
 * @date 2016-3-29 上午11:51:31
 */
public class TextMessage extends BaseMessage {
	
	private String Content; // 文本消息内容
	
	private String MsgId;
	
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
	
}
