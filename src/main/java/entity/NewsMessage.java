/**  
 * @author wangyi
 * @date 2016-3-30 下午2:58:33 
 * @version V1.0   
 */ 
package entity;

import java.util.List;

/**图文消息--主体
 * @author wangyi
 * @date 2016-3-30 下午2:58:33 
 */
public class NewsMessage extends BaseMessage {
	
	private int ArticleCount; //消息数量
	
	private List<News> Articles; //消息体

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<News> getArticles() {
		return Articles;
	}

	public void setArticles(List<News> articles) {
		Articles = articles;
	}
	
	
}
