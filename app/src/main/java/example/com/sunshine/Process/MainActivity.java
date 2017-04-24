package example.com.sunshine.Process;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import example.com.sunshine.MyActivity;
import example.com.sunshine.R;
import example.com.sunshine.UserMessage;
import example.com.sunshine.service.ActivityMessenger;
import example.com.sunshine.service.Test1Service;
import example.com.sunshine.service.TestService;

/**
 * Created by qianxiangsen on 2017/3/31.
 */

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricess);



        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this,TestService.class));
                startActivity(new Intent(MainActivity.this,ActivityMessenger.class));
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG", "MainActivity--"+"onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG", "MainActivity--"+"onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG", "MainActivity--"+"onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG", "MainActivity--"+"onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "MainActivity--"+"onDestroy: ");
    }
}
