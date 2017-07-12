package example.com.sunshine.download;

import android.app.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import example.com.sunshine.R;


public class MainActivity extends Activity implements View.OnClickListener{

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        findViewById(R.id.btnListView).setOnClickListener(this);
        findViewById(R.id.btnRecyclerView).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, DownloadActivity.class);
        switch (v.getId()) {
            case R.id.btnListView:
                intent.putExtra("EXTRA_TYPE", DownloadActivity.TYPE.TYPE_LISTVIEW);
                break;
            case R.id.btnRecyclerView:
                intent.putExtra("EXTRA_TYPE", DownloadActivity.TYPE.TYPE_RECYCLERVIEW);
                break;
            default:
                return;
        }
        startActivity(intent);
    }
}
