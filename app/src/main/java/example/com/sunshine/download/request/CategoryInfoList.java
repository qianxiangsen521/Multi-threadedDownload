package example.com.sunshine.download.request;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryInfoList implements Serializable {
	private String categoryName;
	private int type;
	ArrayList<CategoryInfo> categoryList;
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getCategoryType() {
		return type;
	}
	public void setCategoryType(int curType) {
		this.type = curType;
	}
	public ArrayList<CategoryInfo> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(ArrayList<CategoryInfo> categoryList) {
		this.categoryList = categoryList;
	}

	

}
