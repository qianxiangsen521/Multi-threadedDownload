package example.com.sunshine.single;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import example.com.sunshine.R;

public class ActivityStandard extends BaseActivity  {
    private Button jump;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);

        jump= (Button) findViewById(R.id.btn_standard);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityStandard.this, ActivityStandard.class);
                startActivity(intent);
            }
        });

    }
}