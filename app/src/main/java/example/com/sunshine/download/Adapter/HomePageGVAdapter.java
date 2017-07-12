package example.com.sunshine.download.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import example.com.sunshine.R;
import example.com.sunshine.download.Application.TLiveApplication;
import example.com.sunshine.download.Http.entity.RadioInfo;
import example.com.sunshine.download.Utils.ImageUtils;
import example.com.sunshine.download.request.CategoryRadioInfo;

public class HomePageGVAdapter extends BaseAdapter {
	private CategoryRadioInfo categoryRadioInfo;
	private Context context;
	public ArrayList<RadioInfo> radioInfos;

	public HomePageGVAdapter(ArrayList<RadioInfo> radioInfos, Context vcontext) {
		// TODO Auto-generated constructor stub
		this.context = vcontext;
		this.radioInfos = radioInfos;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (radioInfos == null)
			return 0;
		return radioInfos.size();

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (radioInfos == null)
			return 0;
		return radioInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (radioInfos == null || radioInfos.size() <= 0) {
			return null;
		}
		RadioInfo info = radioInfos.get(position);
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = View.inflate(this.context,
					R.layout.category_radio_item, null);
		}
		TextView radioDes = (TextView) convertView
				.findViewById(R.id.gv_radio_des);
		ImageView radioImage = (ImageView) convertView
				.findViewById(R.id.gv_radio_img);
		RelativeLayout rLayout = (RelativeLayout) convertView
				.findViewById(R.id.rl_img);
//		radioDes.setTypeface(Font.getFont());
//		radioDes.getPaint().setFakeBoldText(true);
		int topicWidth = createTopicWidth();
		rLayout.getLayoutParams().width = topicWidth;
		rLayout.getLayoutParams().height = topicWidth;
		radioDes.getLayoutParams().width = topicWidth;
		convertView.setTag(info);
		radioDes.setText(info.getRecommed());
		loadImage(info.getImgUrl(), radioImage);

		return convertView;
	}

	class ViewHolder {
		ImageView radioImage;
		TextView radioDes;
	}

	private void loadImage(String imgUrl, ImageView imageView) {
//		ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
//		DisplayImageOptions options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.home_recommend_album_bg)
//				.showImageForEmptyUri(R.drawable.home_recommend_album_bg)
//				.showImageOnFail(R.drawable.home_recommend_album_bg)
//				.cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
//				.bitmapConfig(Bitmap.Config.RGB_565).build();
//		ImageLoader.getInstance().init(
//				ImageLoaderConfiguration.createDefault(context));
//		ImageLoader.getInstance().displayImage(imgUrl, imageView, options);
		ImageUtils.loadImg(context,imgUrl,R.mipmap.home_recommend_album_bg,imageView);
	}



	private int createTopicWidth() {
		int width = Math.min(TLiveApplication.mScreenWidth,
				TLiveApplication.mScreenHeight);
		int margin = (int) (5D * (double) context.getResources()
				.getDimensionPixelSize(R.dimen.topic_item_margin));
		return (int) ((double) (width - margin) / 3 + (double) (width - margin) % 3);
	}
}
