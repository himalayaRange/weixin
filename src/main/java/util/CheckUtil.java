/**  
 * @author wangyi
 * @date 2016-3-29 上午10:14:44 
 * @version V1.0   
 */ 
package util;

import java.util.Arrays;

import base.Constant;

/** 验证工具
 * @author wangyi
 * @date 2016-3-29 上午10:14:44 
 */
public class CheckUtil {

	public static boolean checkSignature(String signature,String timestamp,String nonce){
		
		String[] arr=new String[]{Constant.token,timestamp,nonce};
		
		//排序
		Arrays.sort(arr);
		
		//生成字符串
		StringBuffer content=new StringBuffer();
		for(int i=0;i<arr.length;i++){
			content.append(arr[i]);
		}
		
		//SHA1加密
		String temp = SecurityUtil.SHA1(content.toString());
		
		return temp.equals(signature);
	}
}
