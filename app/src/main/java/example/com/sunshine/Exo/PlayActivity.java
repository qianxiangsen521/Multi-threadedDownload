package example.com.sunshine.Exo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.util.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Formatter;
import java.util.Locale;

import example.com.sunshine.Exo.E.MessageEvent;
import example.com.sunshine.Exo.E.NextEvent;
import example.com.sunshine.Exo.E.PlayEvent;
import example.com.sunshine.R;
import example.com.sunshine.fragment.AudioVisualizationFragment;

/**
 * Created by qianxiangsen on 2017/7/11.
 */

public class PlayActivity extends AppCompatActivity implements View.OnClickListener,SeekBar.OnSeekBarChangeListener {

    private static final int PROGRESS_BAR_MAX = 1000;


    // 播放进度展示
    private SeekBar playerSeekBar;

    private long totalDuration;

    private TextView durationView;
    private TextView positionView;
    private StringBuilder formatBuilder;

    private  Formatter formatter;

    private boolean dragging;
    private PlayInfo playInfo;
    private ImageButton pauseButton;
    private ImageButton nextButton;
    private ImageButton previousButton;
    private ImageButton playButton;
    private boolean playing;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity);
        Intent intent  = getIntent();
        formatBuilder = new StringBuilder();
        formatter = new Formatter(formatBuilder, Locale.getDefault());
        playInfo = new PlayInfo();
        playerSeekBar = (SeekBar) findViewById(R.id.exo_progress);
        nextButton = (ImageButton) findViewById(R.id.exo_next);
        if (nextButton != null) {
            nextButton.setOnClickListener(this);
        }
        pauseButton = (ImageButton) findViewById(R.id.exo_pause);
        if (pauseButton != null) {
            pauseButton.setOnClickListener(this);
        }
        previousButton = (ImageButton)findViewById(R.id.exo_prev);
        if (previousButton != null) {
            previousButton.setOnClickListener(this);
        }
        if (playerSeekBar != null) {
            playerSeekBar.setOnSeekBarChangeListener(this);
            playerSeekBar.setMax(PROGRESS_BAR_MAX);
        }
        playButton = (ImageButton) findViewById(R.id.exo_play);
        if (playButton != null) {
            playButton.setOnClickListener(this);
        }
        durationView = (TextView) findViewById(R.id.exo_duration);
        positionView = (TextView) findViewById(R.id.exo_position);

        example.com.sunshine.util.Util.addFragment(getSupportFragmentManager(),R.id.container,
                AudioVisualizationFragment.newInstance(),"AudioVisualizationFragment");
        playInfo.setPlayUrl(intent.getStringExtra("url"));
        PlayManager.play(this,playInfo);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exo_pause:
                PlayManager.pause(this,playInfo);
                break;
            case R.id.exo_next:
                PlayManager.next(this,playInfo);
                break;
            case R.id.exo_prev:
                PlayManager.previous(this,playInfo);
                break;
            case R.id.exo_play:
                PlayManager.restart(this,playInfo);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPlayEvent(PlayEvent event){
        playing = event.isPlayWhenReady();
        boolean requestPlayPauseFocus = false;
        if (playButton != null) {
            requestPlayPauseFocus |= playing && playButton.isFocused();
            playButton.setVisibility(playing ? View.GONE : View.VISIBLE);
        }
        if (pauseButton != null) {
            requestPlayPauseFocus |= !playing && pauseButton.isFocused();
            pauseButton.setVisibility(!playing ? View.GONE : View.VISIBLE);
        }
        if (requestPlayPauseFocus) {
            requestPlayPauseFocus();
        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNextEvent(NextEvent event){
        setButtonEnabled(event.isEnablePrevious() , previousButton);
        setButtonEnabled(event.isEnableNext(), nextButton);
        if (playerSeekBar != null) {
            playerSeekBar.setEnabled(event.isSeekable());
        }
    }

    private void setButtonEnabled(boolean enabled, View view) {
        if (view == null) {
            return;
        }
        view.setEnabled(enabled);
        if (Util.SDK_INT >= 11) {
            setViewAlphaV11(view, enabled ? 1f : 0.3f);
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @TargetApi(11)
    private void setViewAlphaV11(View view, float alpha) {
        view.setAlpha(alpha);
    }


    private void requestPlayPauseFocus() {
        if (!playing && playButton != null) {
            playButton.requestFocus();
        } else if (playing && pauseButton != null) {
            pauseButton.requestFocus();
        }
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        totalDuration = event.getmDuration();
        if (playerSeekBar != null) {
            if (!dragging) {
                playerSeekBar.setProgress(progressBarValue(event.getmCurrentPosition()));
            }
            playerSeekBar.setSecondaryProgress(progressBarValue(event.getmBufferedPosition()));
        }
        if (durationView != null) {
            durationView.setText(stringForTime(totalDuration));
        }
        if (positionView != null && !dragging) {
            positionView.setText(stringForTime(event.getmCurrentPosition()));
        }



    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        dragging = true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            long position = positionValue(progress);
            if (positionView != null) {
                positionView.setText(stringForTime(position));
            }
            if (!dragging) {
                playInfo.setPosition(position);
                PlayManager.seek(this,playInfo);
            }
        }
    }


    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        dragging = false;
        playInfo.setPosition(positionValue(playerSeekBar.getProgress()));
        PlayManager.seek(this,playInfo);
    }

    private int progressBarValue(long position) {
        long duration = totalDuration;
        return duration == C.TIME_UNSET || duration == 0 ? 0
                : (int) ((position * PROGRESS_BAR_MAX) / duration);
    }

    private String stringForTime(long timeMs) {
        if (timeMs == C.TIME_UNSET) {
            timeMs = 0;
        }
        long totalSeconds = (timeMs + 500) / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;
        formatBuilder.setLength(0);
        return hours > 0 ? formatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
                : formatter.format("%02d:%02d", minutes, seconds).toString();
    }
    private long positionValue(int progress) {
        return totalDuration == C.TIME_UNSET ? 0 : ((totalDuration * progress) / PROGRESS_BAR_MAX);
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_bottom, R.anim.slide_top_bottom);
    }
}
