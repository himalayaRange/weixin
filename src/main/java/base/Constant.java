/**  
 * @author wangyi
 * @date 2016-4-7 下午1:45:42 
 * @version V1.0   
 */ 
package base;

/**
 * @author wangyi
 * @date 2016-4-7 下午1:45:42 
 */
public class Constant {
	/******************票据相关*******************************************************************************/
	public static final String token="immoc";
	
	public static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	public static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	
	public static final String CREATE_MENU="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	public static final String QUERY_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN"; //菜单的查询
	
	public static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN"; //删除菜单
	
	public static final String APPID="XXXXXXXXXXXXXXXXXX"; //保证有足够的接口权限
	
	public static final String APPSECRET="YYYYYYYYYYYYYYY";

	/******************消息类型*******************************************************************************/
	public  static final String MESSAGE_TEXT="text";
	
	public  static final String MESSAGE_NEWS="news";
	
	public  static final String MESSAGE_IMAGE="image";
	
	public static final String MESSAGE_MUSIC = "music";
	
	public  static final String MESSAGE_VOICE="voice";
	
	public  static final String MESSAGE_VIDEO="video";
	
	public  static final String MESSAGE_LINK="link";
	
	public  static final String MESSAGE_EVENT="event";
	
	public  static final String MESSAGE_SUBSCRIBE="subscribe";
	
	public  static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
	
	public  static final String MESSAGE_CLICK="CLICK";
	
	public  static final String MESSAGE_VIEW="VIEW";
	
	public static final String MESSAGE_SCANCODE= "scancode_push";

	public static final String MESSAGE_LOCATION = "location"; //地理位置
	
	public static final String MESSAGE_PIC_PHOTO_OR_ALBUM = "pic_photo_or_album"; //弹出拍照或者相册发图的事件推送
	

}
