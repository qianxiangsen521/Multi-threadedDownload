package example.com.sunshine.download.request;

import java.util.ArrayList;

import example.com.sunshine.download.Http.entity.RadioInfo;

public class CommonHomeMoreAndCategoryInfo {
	private String cateName;//顶部显示标题
	private String titleName;
	private String moreUrl;
	private ArrayList<RadioInfo> radioInfos;

	public String getCateType() {
		return cateName;
	}

	public void setCateType(String cateName) {
		this.cateName = cateName;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getMoreUrl() {
		return moreUrl;
	}

	public void setMoreUrl(String moreUrl) {
		this.moreUrl = moreUrl;
	}

	public ArrayList<RadioInfo> getRadioInfos() {
		return radioInfos;
	}

	public void setRadioInfos(ArrayList<RadioInfo> radioInfos) {
		this.radioInfos = radioInfos;
	}

}
