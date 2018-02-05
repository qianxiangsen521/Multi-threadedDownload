package example.com.sunshine.Exo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.cnr.voicetv.ServiceManage;

import example.com.sunshine.R;

public class MainCnrActivity extends AppCompatActivity {
    private Button mBtnAidl;

    ServiceManage serviceManage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnAidl = (Button) findViewById(R.id.test);
        serviceManage = ServiceManage.init(this);
        serviceManage.bindService();

        mBtnAidl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceManage.sendMessage("cnr");
                }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        serviceManage.stopService();
    }
}
