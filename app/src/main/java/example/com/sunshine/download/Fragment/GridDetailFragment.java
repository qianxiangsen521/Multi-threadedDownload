package example.com.sunshine.download.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import example.com.sunshine.R;
import example.com.sunshine.download.Application.TLiveApplication;
import example.com.sunshine.download.Utils.ImageUtils;

/**
 * Created by qianxiangsen on 2017/3/27.
 */

public class GridDetailFragment extends Fragment {

    private Toolbar mToolbar;

    private ImageView image_view;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_detail_grid,null);

        image_view = (ImageView) view.findViewById(R.id.image_view);
        mToolbar = (Toolbar)view.findViewById(R.id.app_bar);
        int topicWidth = createTopicWidth();
        image_view.getLayoutParams().width = topicWidth;
        image_view.getLayoutParams().height = topicWidth;
        Bundle arguments = getArguments();
        String transitionName = arguments.getString("transitionName");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            image_view.setTransitionName(transitionName);
        }
        ImageUtils.loadImg(getActivity(),arguments.getString("image"),
                R.mipmap.home_recommend_album_bg,image_view);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
            }
        });

        return view;
    }

    private int createTopicWidth() {
        int width = Math.min(TLiveApplication.mScreenWidth,
                TLiveApplication.mScreenHeight);
        int margin = (int) (5D * (double) getResources()
                .getDimensionPixelSize(R.dimen.topic_item_margin));
        return (int) ((double) (width - margin) / 3 + (double) (width - margin) % 3);
    }
}
