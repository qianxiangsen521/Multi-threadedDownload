package example.com.sunshine.Main;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import example.com.sunshine.R;

public class AnimatorActivity extends AppCompatActivity {

    //url = "http://slb2.cnrcloudfm.com/10.163.236.99/anyradio-album/1058/1058335.mp3"

    static String baconTitle = "Bacon";
    static String baconText = "Bacon ipsum dolor amet pork belly meatball kevin spare ribs. Frankfurter swine corned beef meatloaf, strip steak.";
    static String veggieTitle = "Veggie";
    static String veggieText = "Veggies es bonus vobis, proinde vos postulo essum magis kohlrabi welsh onion daikon amaranth tatsoi tomatillo melon azuki bean garlic.";

    private ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_test);

        Picasso.with(this).load("").into(new ImageView(this));
        //Linux Kerenl
        // android是一个完整的软件栈 其基础是Linux内核 处理各种低级别的任务 如硬件 驱动程序和电源管理
        // C/C++库
        // 像LibsC adnroid Runtime 在往上是一些核心C/C++库 像LibsC和SQLite以及android运行时间和核心Android库 你的应用
        // Application Framewrok
        // 你的应用使用应用框架中提供的类和服务，在自己的运行时间实例中运行
        // Application Layer
        // 在此之上是你应用层 包括你的应用和安装的每一个其他应用

        // 所以你在Android studio 中点击“运行”后 发生的第一件事是你代码被编译成可以在设备的运行时间内运行的字节码。
        //在Android studio 中 这个过程使用 Gradel完成 这是一款管理依赖关系并可以自定义构建逻辑的构建工具包。
        //我们先使用你的项目Gradel构建并将你的字节码与你的应用资源 外部化图像，ui xml 打包到一个Android 应用程序包文件
        //和android 安装包(apk) 这是一个特定格式化的zip文件 一旦apk就绪Android studio 对其进行签名
        // 然后使用android Debug Bridge（ADB）将其推送到设备 当我们回到终端时 你会看到adb可以在任何物理或模拟设备上与应用交互
        //和调试应用

        // Android 应用是什么
        // 它是一系列相互搭配 并基于Android框架运作的组件 Activity Service BroadcastReceivers ContentProviders
        // android 知道这些组件的每一个 因为它们在Android清单文件中进行了注册
        // Activity
        // Activity 是负责大多数应用用户互动的Android组件 因此activity 负责创建应用程序用于绘制和接收事件的窗口 列如系统中的触摸事件
        // 系统将这些activity维持在堆栈上 以帮助为用户保存上下文，当你使用后退按钮退出activity 是 系统将显示堆栈上的一个activity
        //或者如果堆栈为空 则返回启动器
        // 那么启动器处于代码中什么位置呢？
        //http
        //为了获取http连接 我们只需要对URL调用OpenConnection 注意 这实际并不直接与网络交流，而只是创建http  URL连接对象
        //在这一步 我们可以设置请求方法或添加头字段，更改连接属性，然后从OpenConnection获得一个输入流 接下来我们需要读取这个
        //输入流的内容，在java中很对进行读取的方法，旦我们选择使用扫描器 用它来令牌化流 因为这样更简单快速 通过分割线设置为\\A既从起点开始
        //我们强制扫描器将将流的整个内容读入下一个令牌流

        //用户权限
        //在安装每个apk时 它都回有自己唯一的linux用户ID,而每个应用都在自己的Android runtime 实例中运行，结果每个应用都完全封装在
        //沙盒中它的文件 进程和其他资源都无法被其他应用访问 此沙盒方法用来确保在默认情况下，没有应用可以访问会对其他应用，操作系统

        //线程的基础知识
        //当你尝试在主线程上访问网络时，Android抛出一个异常
        //像任何操作系统一样 android支持同时执行对个任务，每个Android应用都可以分为多个执行线程，这些线程可以同时运行，在现在Android设备上。
        //这些线程可以有操作系统安排，在不同cpu内核上，但系统可能会选择分隔单个cpu的时间片，也就是说每个线程分别运行一段时间，


//        ViewAnimationUtils.createCircularReveal(View view,
//        int centerX,  int centerY, float startRadius, float endRadius) {
//            )

            //centerX centerY
            //这是我们将要进行操作的视图 中心点x和y用来执行过度动画

            //startRadius endRadius
            //startRadius 动画开始的半径 endRadius 动画结束的半径


        ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout)).setTitle(getString(R.string.app_name));
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
                return new ViewHolder(getLayoutInflater().inflate(R.layout.list_item, parent, false));
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                viewHolder.text1.setText(baconTitle);
                viewHolder.text2.setText(baconText);
            }

            @Override
            public int getItemCount() {
                return 10;
            }
        });

    }

    private static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text1;
        TextView text2;
        static int green;
        static int white;

        public ViewHolder(View itemView) {
            super(itemView);
            text1 = (TextView) itemView.findViewById(android.R.id.text1);
            text2 = (TextView) itemView.findViewById(android.R.id.text2);
            itemView.setOnClickListener(this);

            if (green == 0)
                green = itemView.getContext().getResources().getColor(R.color.colorAccent);
            if (white == 0)
                white = itemView.getContext().getResources().getColor(R.color.background_material_light);
        }

        @Override
        public void onClick(View view) {
            boolean isVeggie = ((ColorDrawable)view.getBackground()) != null && ((ColorDrawable)view.getBackground()).getColor() == green;

            TransitionManager.beginDelayedTransition((ViewGroup) view);
            //半宽半高的斜边
            int finalRadius = Math.max(view.getWidth(), view.getHeight()) / 2;

            if (isVeggie) {
                text1.setText(baconTitle);
                text2.setText(baconText);
                view.setBackgroundColor(white);
            } else {
                Animator anim = ViewAnimationUtils.createCircularReveal(view, (int) view.getWidth()/2, (int) view.getHeight()/2, 0, finalRadius);
                text1.setText(veggieTitle);
                text2.setText(veggieText);
                view.setBackgroundColor(green);
                anim.start();
            }
        }
    }



//    }
//
//    public static String getResponseFromHttpUrl(URL url) throws IOException{
//        HttpURLConnection urlConnection  = (HttpURLConnection)url.openConnection();
//        try{
//            InputStream in = urlConnection.getInputStream();
//            Scanner scanner = new Scanner(in);
//            scanner.useDelimiter("\\A");
//            boolean hsaInput = scanner.hasNext();
//            if (hsaInput){
//                return scanner.next();
//            }else{
//                return  null;
//            }
//        }finally {
//            urlConnection.disconnect();
//        }
//
//    }
}
