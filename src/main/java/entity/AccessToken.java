/**  
 * @author wangyi
 * @date 2016-4-6 下午2:00:33 
 * @version V1.0   
 */ 
package entity;

/** Access token返回结果
 * @author wangyi
 * @date 2016-4-6 下午2:00:33 
 */
public class AccessToken {
	
	private String access_token; //获取到的凭证
	
	private int expires_in;// 凭证的有效时间

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	
	
}
