package example.com.sunshine.Main;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/3/24.
 */

public class SlideActivity extends AppCompatActivity{

    private Button test;
    private ImageView icon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test = (Button) findViewById(R.id.test);

        icon = (ImageView) findViewById(R.id.icon);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int finalState = icon.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE;

                if (Build.VERSION.SDK_INT < 21) {
                    icon.setVisibility(finalState);
                } else {
                    Slide slide = new Slide();
                    slide.setSlideEdge(Gravity.TOP);

                    final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) SlideActivity.this
                            .findViewById(android.R.id.content)).getChildAt(0);
                    TransitionManager.beginDelayedTransition(viewGroup, slide);

                    icon.setVisibility(finalState);
                }

            }
        });
    }
}
