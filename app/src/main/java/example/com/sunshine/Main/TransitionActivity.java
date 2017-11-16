package example.com.sunshine.Main;

import android.animation.TimeInterpolator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/3/24.
 */

public class TransitionActivity extends AppCompatActivity{
    String[][] mColorSet = {
            {"#D32F2F", "#F44336", "#FFCDD2", "#FFFFFF", "#448AFF", "#212121", "#727272", "#B6B6B6"},
            {"#0288D1", "#03A9F4", "#B3E5FC", "#FFFFFF", "#9E9E9E", "#212121", "#727272", "#B6B6B6"},
            {"#FFA000", "#FFC107", "#FFECB3", "#FFFFFF", "#9E9E9E", "#212121", "#727272", "#B6B6B6"},
            {"#00796B", "#009688", "#B2DFDB", "#FFFFFF", "#9E9E9E", "#212121", "#727272", "#B6B6B6"},
            {"#E64A19", "#FF5722", "#FFCCBC", "#FFFFFF", "#448AFF", "#212121", "#727272", "#B6B6B6"},
            {"#512DA8", "#673AB7", "#D1C4E9", "#FFFFFF", "#03A9F4", "#212121", "#727272", "#B6B6B6"},
    };

    public final static int DARK_PRIMARY   = 0;
    public final static int PRIMARY        = 1;
    public final static int LIGHT_PRIMARY  = 2;
    public final static int TEXT           = 3;
    public final static int ACCENT         = 4;
    public final static int PRIMARY_TEXT   = 5;
    public final static int SECONDARY_TEXT = 6;
    public final static int DIVIDER        = 7;

    @BindView(R.id.grid) protected GridView mGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        setupGrid();
    }

    protected void setupGrid() {
        int cellHeight = getTileHeight();
        ColorSetAdapter adapter = new ColorSetAdapter(this, mColorSet, cellHeight);
        mGrid.setAdapter(adapter);

        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                onPositionSelected(pos);
            }
        });
    }

    protected int getTileHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusHeight = 0;
        if (resourceId > 0) {
            statusHeight = getResources().getDimensionPixelSize(resourceId);
        }
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int height = metrics.heightPixels - statusHeight;

        int cols = 2; // mGrid.getNumCols();
        int rows = (mColorSet.length + 1) / cols;
        return height / rows;
    }

    protected void onPositionSelected(int pos) {
        Intent intent = new Intent(this, TransitionDetailActivity.class);
        intent.putExtra(TransitionDetailActivity.KEY_COLORS, mColorSet[pos]);

        View tile = mGrid.getChildAt(pos);
        // Check more in:
        // http://developer.android.com/reference/android/support/v4/app/ActivityOptionsCompat.html
        // https://github.com/codepath/android_guides/wiki/Shared-Element-Activity-Transition
        Bundle bundle = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, tile, "grid_element")
                .toBundle();

        if (Build.VERSION.SDK_INT >= 21) {
            startActivity(intent, bundle);
        } else {
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public class ColorSetAdapter extends BaseAdapter {
        String[][] mColorSet;
        Context mContext;
        int mHeight;

        public ColorSetAdapter(Context c, String[][] colorSet, int height) {
            mContext = c;
            mColorSet = colorSet;
            mHeight = height;
        }

        public long getItemId(int position) {
            return (long) Arrays.hashCode(mColorSet[position]);
        }

        public String[] getItem(int position) {
            return mColorSet[position];
        }

        public int getCount() {
            return mColorSet.length;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            View tile;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                tile = new ImageView(mContext);
                tile.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, mHeight));
            } else {
                tile = convertView;
            }

            String[] colors = getItem(position);
            tile.setBackgroundColor(Color.parseColor(colors[PRIMARY]));

            if (Build.VERSION.SDK_INT >= 21) {
                tile.setTransitionName("grid_element");
            }
            return tile;
        }
    }
}
