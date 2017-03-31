/**  
 * @author wangyi
 * @date 2016-4-7 下午4:00:25 
 * @version V1.0   
 */ 
package menu;

/**按钮
 * @author wangyi
 * @date 2016-4-7 下午4:00:25 
 */
public class Button {

	private String type;//菜单类型
	
	private String name;//菜单名称
	
	private Button[] sub_button;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
	
	
}
