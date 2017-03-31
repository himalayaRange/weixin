/**  
 * @author wangyi
 * @date 2016-3-29 上午11:34:35 
 * @version V1.0   
 */ 
package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import base.Constant;
import com.thoughtworks.xstream.XStream;
import entity.Image;
import entity.ImageMessage;
import entity.Music;
import entity.MusicMessage;
import entity.News;
import entity.NewsMessage;
import entity.TextMessage;


/** 微信服务器和微信客户端消息的交互是通过XML进行解析的
 * @author wangyi
 * @date 2016-3-29 上午11:34:35 
 */
public class MessageUtil {
	
	/*
	 * Xml转 Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String,String> map=new HashMap<String,String>();
		
		SAXReader reader=new SAXReader();
		InputStream is= request.getInputStream();
		Document doc = reader.read(is);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for(Element ele:list){
			map.put(ele.getName(), ele.getText());
		}
		return map;
	}
	
	/*
	 * 对象类型转化为XML对象<文本对象>
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xStream = new XStream();
		xStream.alias("xml", textMessage.getClass());
		String xml = xStream.toXML(textMessage);
		return xml;
	}
	
	
	/*
	 * 初始化文本消息
	 */
	public static String initText(String FromUserName,String ToUserName,String Content){
		TextMessage tm=new TextMessage();
		tm.setFromUserName(ToUserName);
		tm.setToUserName(FromUserName);
		tm.setCreateTime(new Date().getTime());
		tm.setMsgType(Constant.MESSAGE_TEXT);
		tm.setContent(Content);
		return textMessageToXml(tm);
	}
	
	/*
	 * 图文消息转化为XML对象<图文对象>
	 */
	public static String newsMessageToXml(NewsMessage newsMessage){
		XStream xStream = new XStream();
		xStream.alias("xml", newsMessage.getClass());
		xStream.alias("item", new News().getClass());
		String xml = xStream.toXML(newsMessage);
		return xml;
	}
	
	/*
	 * 初始化图文消息
	 */
	public static String initNewsMessage(String FromUserName,String ToUserName){
		String message=null;
		List<News> newsList=new ArrayList<News>();
		NewsMessage newsMessage=new NewsMessage();
		/*
		 * 消息体
		 */
		News news = new News();
		news.setTitle("慕课网介绍");
		news.setDescription("慕课网是垂直的互联网IT技能免费学习网站。以独家视频教程、在线编程工具、学习计划、问答社区为核心特色。在这里，你可以找到最好的互联网技术牛人，也可以通过免费的在线公开视频课程学习国内领先的互联网IT技术。慕课网课程涵盖前端开发、PHP、Html5、Android、iOS、Swift等IT前沿技术语言，包括基础课程、实用案例、高级分享三大类型，适合不同阶段的学习人群。");
		news.setPicUrl("http://wangyi.ngrok.natapp.cn/weixin/image/imooc.jpg");
		//news.setUrl("www.imooc.com");
		news.setUrl("www.duolaopiaoju.com");
		newsList.add(news);
		/*
		 * 消息
		 */
		newsMessage.setToUserName(FromUserName);
		newsMessage.setFromUserName(ToUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(Constant.MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());
		/*
		 * 转化为XML发送给微信后台服务器
		 */
		message = newsMessageToXml(newsMessage);
		return message;
	}
	
	
	/*
	 * 组装图片消息
	 */
	public static String initImageMessage(String fromUserName,String toUserName,String MediaId){
		String message = null;
		Image image = new Image();
		image.setMediaId(MediaId);
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(Constant.MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		message = imageMessageToXml(imageMessage);
		return message;
	} 
	
	/**
	 * 图片消息转为xml
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	
	
	/**
	 * 组装音乐消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusicMessage(String fromUserName,String toUserName,String mediaId){
		String message = null;
		Music music = new Music();
		music.setThumbMediaId(mediaId);
		music.setTitle("see you again");
		music.setDescription("速7片尾曲");
		music.setMusicUrl("http://wangyi.ngrok.natapp.cn/weixin/music/See You Again.mp3");
		music.setHQMusicUrl("http://wangyi.ngrok.natapp.cn/weixin/music/See You Again.mp3");
		
		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setFromUserName(toUserName);
		musicMessage.setToUserName(fromUserName);
		musicMessage.setMsgType(Constant.MESSAGE_MUSIC);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMusic(music);
		message = musicMessageToXml(musicMessage);
		return message;
	}
	
	
	/**
	 * 音乐消息转为xml
	 * @param musicMessage
	 * @return
	 */
	public static String musicMessageToXml(MusicMessage musicMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}
	
	/*******************************************************************************************************************************/
	/*
	 * 主菜单
	 */
	public static String menuText(){
		StringBuffer sb=new StringBuffer();
		sb.append("欢迎您的关注，请按照菜单提示进行操作\n\n");
		sb.append("1.课程介绍\n");
		sb.append("2.慕课网介绍\n");
		sb.append("3.图文消息\n");
		sb.append("4.音乐消息\n");
		sb.append("5.翻译\n");
		sb.append("spring.春天\n");
		sb.append("summer.夏天\n");
		sb.append("autumn.秋天\n");
		sb.append("winter.冬天\n");
		sb.append("回复?调出此菜单");
		return sb.toString();
	}
	
	
	public static String threeMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("词组翻译使用指南\n\n");
		sb.append("使用示例：\n");
		sb.append("输入中文或则英文\n");
		sb.append("回复？显示主菜单。");
		return sb.toString();
	}
	
	public static String firstMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("本套课程介绍微信公众号的开发，主要涉及公众号的介绍，编辑模式，开发模式");
		return sb.toString();
	}
	
	public static String secondMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("慕课网是垂直的互联网IT技能免费学习网站。以独家视频教程、在线编程工具、学习计划、问答社区为核心特色。在这里，你可以找到最好的互联网技术牛人，也可以通过免费的在线公开视频课程学习国内领先的互联网IT技术。慕课网课程涵盖前端开发、PHP、Html5、Android、iOS、Swift等IT前沿技术语言，包括基础课程、实用案例、高级分享三大类型，适合不同阶段的学习人群。以纯干货、短视频的形式为平台特点，为在校学生、职场白领提供了一个迅速提升技能、共同分享进步的学习平台。[1]");
		return sb.toString();
	}
	/*************************************************************************************************************************************/
}
