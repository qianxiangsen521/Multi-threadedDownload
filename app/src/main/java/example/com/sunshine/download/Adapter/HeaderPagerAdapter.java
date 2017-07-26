package example.com.sunshine.download.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import example.com.sunshine.R;
import example.com.sunshine.download.Fragment.PopularFragment;
import example.com.sunshine.download.Http.Configuration;
import example.com.sunshine.download.Http.entity.PlayUrlItem;
import example.com.sunshine.download.Http.entity.RadioInfo;
import example.com.sunshine.download.Utils.Helpers;
import example.com.sunshine.download.Utils.ImageUtils;
import example.com.sunshine.util.Util;


public class HeaderPagerAdapter extends PagerAdapter {


    public ArrayList<RadioInfo> radioInfos;
    private PopularFragment homeFragment;
    private RadioInfo radioInfo;
    private View imageview;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case Configuration.ALBUMS_ULR_FLAG:
                    ArrayList<PlayUrlItem> playUrlList = (ArrayList<PlayUrlItem>) msg.obj;
                    if (playUrlList != null && playUrlList.size() > 0) {
                        radioInfo.setAlbumUrlList(playUrlList);
//					FmPlayerManager.play(radioInfo, homeFragment.getActivity());
//					Intent intent = new Intent(homeFragment.getActivity(),
//							DetailPlayerActivity.class);
//					intent.putExtra("radioinfo", radioInfo);
//					homeFragment.getActivity().startActivity(intent);
//                        DetailPlayerActivity.lanuchActivity(homeFragment.getActivity(), radioInfo);
                        // homeFragment.startAnimation(imageview);
                    } else {
                        Toast.makeText(homeFragment.getActivity(), "请求播放地址错误", Toast.LENGTH_LONG)
                                .show();
                    }
                    break;

                default:
                    break;
            }
        }

    };
//    private NativeIndividualDownloadOptions nativeIndividualDownloadOptions;

    public HeaderPagerAdapter(PopularFragment homeFragment) {
        this.homeFragment = homeFragment;
        radioInfos = new ArrayList<RadioInfo>();
    }

    @Override
    public int getCount() {
        return radioInfos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        // TODO Auto-generated method stub
        return view == obj;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }

    public ViewHolder viewHolder;

    @Override
    public Object instantiateItem(View container, int position) {
        if (radioInfos.size() <= 0) {
            return null;
        }
        final View view = LayoutInflater.from(homeFragment.getActivity()).inflate(
                R.layout.homeheaderitem, null);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (!Helpers.isNetworkAvailable(homeFragment.getActivity())) {
                    Toast.makeText(
                            homeFragment.getActivity(),
                            homeFragment.getActivity().getResources()
                                    .getString(R.string.network_error), Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                radioInfo = (RadioInfo) v.getTag();
                imageview = v;
                String type = radioInfo.getType();
                if (type.equals("8")) {
                    String downloadUrl = radioInfo.getDownloadUrl();
                    if (TextUtils.isEmpty(downloadUrl)){
                        return;
                    }
//                    String aid = radioInfo.getAid();
//                    Intent intent = new Intent(homeFragment.getActivity(),
//                            CeremonyActivity.class);
//                    intent.putExtra("downloadUrl", downloadUrl);
//                    intent.putExtra("aid", aid);
//
//                    homeFragment.getActivity().startActivity(intent);
                } else if(type.equals("10")){
//                    EventBus.getDefault().post(new YoudaoEvent("Youdao"));
                } else {
                    if (type.equals("4") || type.equals("5")) {
                        String url = radioInfo.getAlbumListUrl();
                        String[] args = url.split("aid=");
                        if (args.length >= 1) {
                            String aid = args[1];
                            radioInfo.setAlbumId(Integer.parseInt(aid));
                        }
                    }
                    Util.setIntnetPlay(homeFragment.getActivity().getSupportFragmentManager(),R.id.fragment_play);
//                    DetailPlayerActivity.lanuchActivity(homeFragment.getActivity(), radioInfo);
                }
            }
        });
        viewHolder = new ViewHolder();
        viewHolder.topicView = (ImageView) view
                .findViewById(R.id.home_header_img);
        int showPosition;
        if (position == radioInfos.size() - 1) {
            showPosition = 1;
        } else {
            showPosition = (position % this.radioInfos.size()) + 1;
        }
        RadioInfo radioInfo = radioInfos.get(showPosition);
        view.setTag(radioInfo);
        loadImage(radioInfo.getImgUrl(), viewHolder.topicView);
        ((ViewPager) container).addView(view, 0);
        return view;
    }



    public class ViewHolder {
        ImageView topicView;
    }

    private void loadImage(String imgUrl, ImageView imageView) {

        if (!TextUtils.isEmpty(imgUrl)) {
            Glide.with(homeFragment.getActivity())
                    .load(imgUrl)
                    .crossFade()
                    .into(imageView);
        }
    }


}
