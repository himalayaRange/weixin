/**  
 * @author wangyi
 * @date 2016-3-30 下午2:56:14 
 * @version V1.0   
 */ 
package entity;

/** 图文消息--具体的消息内容
 * @author wangyi
 * @date 2016-3-30 下午2:56:14 
 */
public class News {
	private String Title;
	private String Description;
	private String PicUrl;
	private String Url;
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	
	
}
