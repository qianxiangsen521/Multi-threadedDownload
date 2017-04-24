package example.com.sunshine.Main;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import example.com.sunshine.R;

public class DynamicSurfacesActivity extends AppCompatActivity {

    private final Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                Log.d("TAG", "handleMessage: "+msg.obj.toString());
            }
        }
    };
    private CollapsingToolbarLayout xCollapLayout;
    double d = 5.3E12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_surfaces);
        Log.d("TAG", "onCreate: ");
        ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout)).setTitle(getString(R.string.dynamic_surfaces_title));
//        new Thread(new mRunnable()).start();

//        xCollapLayout.post(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//        xCollapLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 0);
    }

    class mRunnable implements Runnable{

        @Override
        public void run() {
            Message message = Message.obtain();
            message.what = 1;
            message.obj = "text";
            handler.sendMessage(message);
        }
    }

    class mRunUi implements Runnable{
        @Override
        public void run() {

            try {
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                DynamicSurfacesActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }


        }
    }
    public void onClick(View v) {

        new DownloadImageTask().execute("http://example.com/image.png");
    }

    private class DownloadImageTask extends AsyncTask<String, Void, String> {
        /** The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute() */
        protected String doInBackground(String... urls) {
            return urls[0];
        }

        /** The system calls this to perform work in the UI thread and delivers
         * the result from doInBackground() */
        protected void onPostExecute(String result) {
            Log.d("TAG", "onPostExecute: "+result);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("TAG", "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG", "onResume: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("TAG", "onRestart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG", "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG", "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "onDestroy: ");
    }
}
