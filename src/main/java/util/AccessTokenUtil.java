/**  
 * @author wangyi
 * @date 2016-4-6 上午11:15:10 
 * @version V1.0   
 */ 
package util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import menu.Button;
import menu.ClickButton;
import menu.Menu;
import menu.ViewButton;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import trans.BaiduTranslateDemo;
import base.Constant;
import entity.AccessToken;

/**
 * access token工具
 * 如果第三方不使用中控服务器，而是选择各个业务逻辑点各自去刷新access_token，那么就可能会产生冲突，导致服务不稳定
 * 所以一般讲access_token保存在本地
 * @author wangyi
 * @date 2016-4-6 上午11:15:10 
 */
@SuppressWarnings("deprecation")
public class AccessTokenUtil {
	
	/*
	 * GET请求 ,企业级的最好用https进行数据的传输和接受
	 * @Description
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static JSONObject doGetStr(String url) throws ClientProtocolException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		HttpResponse httpResponse = client.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		if(entity != null){
			String result = EntityUtils.toString(entity,"UTF-8");
			jsonObject = JSONObject.fromObject(result);
		}
		return jsonObject;
	}
	
	/*
	 * POST请求 ，,企业级的最好用https进行数据的传输和接受
	 * @Description
	 * @param url
	 * @param outStr
	 * @return
	 */
	@SuppressWarnings("resource")
	public static JSONObject doPostStr(String url,String outStr) throws ClientProtocolException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject=null;
		httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
		HttpResponse response = client.execute(httpPost);
		String result = EntityUtils.toString(response.getEntity(),"UTF-8");
		jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}
	
	
	/*
	 *GET获取access_token 
	 * @Description
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static AccessToken getAccessToken() throws ClientProtocolException, IOException{
		AccessToken token=new AccessToken();
		String url=Constant.ACCESS_TOKEN_URL.replace("APPID", Constant.APPID).replace("APPSECRET", Constant.APPSECRET);
		/**
		 * 通过get方式获取结果
		 */
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject!=null){
			token.setAccess_token(jsonObject.getString("access_token"));
			token.setExpires_in(jsonObject.getInt("expires_in"));
		}
		return token;
	}
	
	
	/*
	 * 文件上传
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String upload(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}
		String url = Constant.UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);
		
		URL urlObj = new URL(url);
		//连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST"); 
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); 

		//设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		//设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		//获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		//输出表头
		out.write(head);

		//文件正文部分
		//把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		//结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			//定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println(jsonObj);
		String typeName = "media_id";
		if(!"image".equals(type)){
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
	
	/*
	 * 组装菜单 
	 * @Description
	 * @return
	 */
	public static Menu initMenu(){
		Menu menu=new Menu();
		ClickButton button11=new ClickButton();
		button11.setName("click菜单");
		button11.setType("click");
		button11.setKey("11");
		
		ViewButton button21=new ViewButton();
		button21.setName("view菜单");
		button21.setType("view");
		button21.setUrl("http://new.rich-star.com/");
		
		ClickButton button31 = new ClickButton();
		button31.setName("扫码事件");
		button31.setType("scancode_push");
		button31.setKey("31");
		
		ClickButton button32 = new ClickButton();
		button32.setName("地理位置");
		button32.setType("location_select");
		button32.setKey("32");
		
		ClickButton button33 = new ClickButton();
		button33.setName("相机相册");
		button33.setType("pic_photo_or_album");
		button33.setKey("33");
		
		Button button=new Button();
		button.setName("生活服务");
		button.setSub_button(new Button[]{button31,button32,button33});
		
		menu.setButton(new Button[]{button11,button21,button});
		
		return menu;
	}
	
	/*
	 * POST初始化菜单 
	 * @Description
	 * @param token
	 * @param menu
	 * @param accessToken
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static int createMenu(String menu,String accessToken) throws ClientProtocolException, IOException{
		int result=0;
		String url=Constant.CREATE_MENU.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = doPostStr(url, menu);
		if(jsonObject!=null){
			result= jsonObject.getInt("errcode");
		}
		return result;
		
	}
	
	/*
	 *菜单的查询 	 
	 * @Description
	 * @param token
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JSONObject queryMenu(String token) throws ClientProtocolException, IOException{
		String url=Constant.QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}
	
	/*
	 * 菜单的删除
	 */
	public static int deleteMenu(String token) throws ParseException, IOException{
		String url =Constant.DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		int result = 0;
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
	
	/*
	 * 翻译 
	 * @Description
	 * @param source
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String translate(String source) {
		String resut=null;
		/*
		 * 进行中英文判断
		 */
		try{
		if(isContainChinese(source)){
			resut=BaiduTranslateDemo.translate(source, "zh", "en");
		}else{
			resut=BaiduTranslateDemo.translate(source, "en", "zh");
		}
		}catch(Exception e){
			
		}
		return resut;
	}
	
	/**
	 * 判读是否是中文
	 * @Description
	 * @param str
	 * @return
	 */
	  public static boolean isContainChinese(String str) {

	        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
	        Matcher m = p.matcher(str);
	        if (m.find()) {
	            return true;
	        }
	        return false;
	    }
}
