package example.com.sunshine.wcdb;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.database.SQLiteOpenHelper;
import com.tencent.wcdb.repair.RepairKit;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.com.sunshine.Exo.ExoService;
import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/7/10.
 */

public class AndroidWxDbTextActivity extends AppCompatActivity {


    @BindView(R.id.recycler_view)
    RecyclerView mReclerView;
    private SQLiteOpenHelper mDBHelper = new DBHelper(this);

    private SQLiteDatabase mDB;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
        ButterKnife.bind(this);




//        mDBHelper.setWriteAheadLoggingEnabled(true);
//        mDB = mDBHelper.getWritableDatabase();

    }


}
