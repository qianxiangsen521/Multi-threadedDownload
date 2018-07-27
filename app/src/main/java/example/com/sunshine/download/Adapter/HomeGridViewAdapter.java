package example.com.sunshine.download.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import example.com.sunshine.R;
import example.com.sunshine.download.Application.TLiveApplication;
import example.com.sunshine.download.Http.entity.RadioInfo;
import example.com.sunshine.download.Utils.ImageUtils;

public class HomeGridViewAdapter extends BaseAdapter {
	public ArrayList<RadioInfo> radioInfos;
	private Context mContext;

	public HomeGridViewAdapter(Context context) {
		this.mContext = context;
	}

	@Override
	public int getCount() {
		if (null == radioInfos)
			return 0;
		return radioInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return radioInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (radioInfos.size() <= 0) {
			return null;
		}
		RadioInfo radioInfo = radioInfos.get(position);
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.home_center_gvitem,
					null);
		}
		convertView.setTag(radioInfo);
		ImageView homeCenterImg = (ImageView) convertView
				.findViewById(R.id.home_center_img);
		int topicWidth = createTopicWidth();
		// homeCenterImg.getLayoutParams().width = topicWidth;
		// homeCenterImg.getLayoutParams().height = topicWidth;
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				topicWidth, topicWidth);
		homeCenterImg.setLayoutParams(lp);
		loadImage(radioInfo.getImgUrl(), homeCenterImg);
		return convertView;
	}

	class ViewHolder {
		ImageView homeCenterImg;
		TextView homeCenterName;
	}

	private void loadImage(String imgUrl, ImageView imageView) {
//		ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
//		DisplayImageOptions options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.home_recommed_index_live_bg)
//				.showImageForEmptyUri(R.drawable.home_recommed_index_live_bg)
//				.showImageOnFail(R.drawable.home_recommed_index_live_bg)
//				.cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
//				.bitmapConfig(Bitmap.Config.RGB_565).build();
//		ImageLoader.getInstance().init(
//				ImageLoaderConfiguration.createDefault(mContext));
//		ImageLoader.getInstance().displayImage(imgUrl, imageView, options);
		ImageUtils.loadImg(mContext,imgUrl,R.mipmap.home_recommend_album_bg,imageView);
	}



	private int createTopicWidth() {
		int width = Math.min(TLiveApplication.mScreenWidth,
				TLiveApplication.mScreenHeight);
		int margin = (int) (5D * (double) mContext.getResources()
				.getDimensionPixelSize(R.dimen.topic_center_item_margin));
		return (int) ((double) (width - margin) / 4.6 + (double) (width - margin) % 4.6);
	}
}
