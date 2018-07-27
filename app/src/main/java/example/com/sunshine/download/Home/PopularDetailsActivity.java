package example.com.sunshine.download.Home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import example.com.sunshine.R;
import example.com.sunshine.download.Utils.ImageUtils;

public class PopularDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_details);
        Intent mTitle = getIntent();
        String imgUrl = mTitle.getStringExtra("image");
        ImageView imageView = (ImageView) findViewById(R.id.iv_name);

        ImageUtils.loadImg(this,imgUrl,
                R.mipmap.home_recommend_album_bg,imageView);
    }
}
