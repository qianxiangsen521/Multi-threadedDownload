package example.com.sunshine.download.request;

import java.util.ArrayList;

import example.com.sunshine.download.Http.entity.RadioInfo;

public class CategoryRadioInfo{
	private String title;
	private String moreContent;
	private ArrayList<RadioInfo> radioInfos;
	private int indexType;

	public CategoryRadioInfo(){

	}

	public int getIndexType() {
		return indexType;
	}

	public void setIndexType(int indexType) {
		this.indexType = indexType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMoreContent() {
		return moreContent;
	}

	public void setMoreContent(String moreContent) {
		this.moreContent = moreContent;
	}

	public ArrayList<RadioInfo> getRadioInfos() {
		return radioInfos;
	}

	public void setRadioInfos(ArrayList<RadioInfo> radioInfos) {
		this.radioInfos = radioInfos;
	}
}
