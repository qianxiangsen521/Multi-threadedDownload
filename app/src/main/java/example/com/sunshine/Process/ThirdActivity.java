package example.com.sunshine.Process;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import example.com.sunshine.R;
import example.com.sunshine.service.TestService;

/**
 * Created by qianxiangsen on 2017/3/31.
 */

public class ThirdActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricess);

        startService(new Intent(ThirdActivity.this,TestService.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG", "ThirdActivity--"+"onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG", "ThirdActivity--"+"onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG", "ThirdActivity--"+"onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG", "ThirdActivity--"+"onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "ThirdActivity--"+"onDestroy: ");
    }
}
