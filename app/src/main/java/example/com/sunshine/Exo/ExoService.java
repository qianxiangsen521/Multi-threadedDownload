package example.com.sunshine.Exo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.drm.UnsupportedDrmException;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import example.com.sunshine.Exo.E.MessageEvent;
import example.com.sunshine.Exo.E.NextEvent;
import example.com.sunshine.Exo.E.PlayEvent;
import example.com.sunshine.R;
import example.com.sunshine.download.Application.TLiveApplication;
import example.com.sunshine.download.Home.Main111Activity;


/**
 * Created by qianxiangsen on 2017/7/10.
 */

public class ExoService extends Service implements  ExoPlayer.EventListener{


    private final Runnable updateProgressAction = new Runnable() {
        @Override
        public void run() {
            updateProgress();
        }
    };

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    private static final long MAX_POSITION_FOR_SEEK_TO_PREVIOUS = 3000;

    private SimpleExoPlayer player;

    private boolean shouldAutoPlay = true;

    private int resumeWindow;

    private long resumePosition;

    private DefaultTrackSelector trackSelector;

    private DataSource.Factory mediaDataSourceFactory;

    private EventLogger eventLogger;

    private Handler mainHandler;

    private boolean playerNeedsSource;

    private Timeline.Window currentWindow;

    private int requestCode;

    private NotificationManager mNotificationManager;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        shouldAutoPlay = true;
        clearResumePosition();
        mediaDataSourceFactory = buildDataSourceFactory(true);
        mainHandler = new Handler();
        currentWindow = new Timeline.Window();
        requestCode = (int) (Math.random() * 10000);

        registerPlayControlReceiver();

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);

        unregisterPlayControlReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        initializePlayer(intent);
        return START_STICKY;
    }
    private void seekTo(long positionMs) {
        seekTo(player.getCurrentWindowIndex(), positionMs);
    }

    private void seekTo(int windowIndex, long positionMs) {
        if (player != null) {
            player.seekTo(windowIndex,positionMs);
            updateProgress();
        }
    }

    private void previous() {
        Timeline currentTimeline = player.getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return;
        }
        int currentWindowIndex = player.getCurrentWindowIndex();
        currentTimeline.getWindow(currentWindowIndex, currentWindow);
        if (currentWindowIndex > 0 && (player.getCurrentPosition() <= MAX_POSITION_FOR_SEEK_TO_PREVIOUS
                || (currentWindow.isDynamic && !currentWindow.isSeekable))) {
            seekTo(currentWindowIndex - 1, C.TIME_UNSET);
        } else {
            seekTo(0);
        }
    }
    private void next() {
        Timeline currentTimeline = player.getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return;
        }
        int currentWindowIndex = player.getCurrentWindowIndex();
        if (currentWindowIndex < currentTimeline.getWindowCount() - 1) {
            seekTo(currentWindowIndex + 1, C.TIME_UNSET);
        } else if (currentTimeline.getWindow(currentWindowIndex, currentWindow, false).isDynamic) {
           seekTo(currentWindowIndex, C.TIME_UNSET);
        }

    }

    public void initializePlayer(Intent paramIntent){
        if (paramIntent == null){
            return;
        }
        String str1 = paramIntent.getAction();
        if (str1.equals(ExoConstants.ACTION_SEEK)){
            seekTo(paramIntent.getLongExtra(ExoConstants.PLAY_POSTITION,0));
            return;
        }
        if (str1.equals(ExoConstants.ACTION_PAUSE)){
            player.setPlayWhenReady(false);
        }
        if (str1.equals(ExoConstants.ACTION_NEXT)){
                next();
        }
        if (str1.equals(ExoConstants.ACTION_PREVIONS)){
                previous();
        }

        if (str1.equals(ExoConstants.ACTIION_RESTART)){
            player.setPlayWhenReady(true);

        }
        if (str1.equals(ExoConstants.ACTION_PLAY)){
            releasePlayer();
            if (player == null) {

                boolean preferExtensionDecoders = paramIntent.getBooleanExtra(ExoConstants.PREFER_EXTENSION_DECODERS, false);
                UUID drmSchemeUuid = paramIntent.hasExtra(ExoConstants.DRM_SCHEME_UUID_EXTRA)
                        ? UUID.fromString(paramIntent.getStringExtra(ExoConstants.DRM_SCHEME_UUID_EXTRA)) : null;
                DrmSessionManager<FrameworkMediaCrypto> drmSessionManager = null;
                if (drmSchemeUuid != null) {
                    String drmLicenseUrl = paramIntent.getStringExtra(ExoConstants.DRM_LICENSE_URL);
                    String[] keyRequestPropertiesArray = paramIntent.getStringArrayExtra(ExoConstants.DRM_KEY_REQUEST_PROPERTIES);
                    Map<String, String> keyRequestProperties;
                    if (keyRequestPropertiesArray == null || keyRequestPropertiesArray.length < 2) {
                        keyRequestProperties = null;
                    } else {
                        keyRequestProperties = new HashMap<>();
                        for (int i = 0; i < keyRequestPropertiesArray.length - 1; i += 2) {
                            keyRequestProperties.put(keyRequestPropertiesArray[i],
                                    keyRequestPropertiesArray[i + 1]);
                        }
                    }
                    try {
                        drmSessionManager = buildDrmSessionManager(drmSchemeUuid, drmLicenseUrl,
                                keyRequestProperties);
                    } catch (UnsupportedDrmException e) {
                        int errorStringId = Util.SDK_INT < 18 ? R.string.error_drm_not_supported
                                : (e.reason == UnsupportedDrmException.REASON_UNSUPPORTED_SCHEME
                                ? R.string.error_drm_unsupported_scheme : R.string.error_drm_unknown);
                        showToast(errorStringId);
                        return;
                    }
                }

                @DefaultRenderersFactory.ExtensionRendererMode int extensionRendererMode =
                        ((TLiveApplication) getApplication()).useExtensionRenderers()
                                ? (preferExtensionDecoders ? DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
                                : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
                                : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;
                TrackSelection.Factory videoTrackSelectionFactory =
                        new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
                trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
                player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, new DefaultLoadControl(),
                        drmSessionManager, extensionRendererMode);
                player.addListener(this);

                eventLogger = new EventLogger(trackSelector);
                player.addListener(eventLogger);
                player.setAudioDebugListener(eventLogger);
                player.setVideoDebugListener(eventLogger);
                player.setMetadataOutput(eventLogger);

                player.setPlayWhenReady(shouldAutoPlay);
                playerNeedsSource = true;
            }
            setplayerUrl(paramIntent);
        }


        showNotifiction();

    }
    private void setplayerUrl(Intent paramIntent){
        if (playerNeedsSource) {
            Uri[] uris;
            String[] extensions;
            if (ExoConstants.ACTION_PLAY.equals(paramIntent.getAction())) {
                String uri = paramIntent.getStringExtra(ExoConstants.PLAY_URL);
                uris = new Uri[]{Uri.parse(uri),Uri.parse(ExoConstants.PLAY_URL_NAME)};
                extensions = new String[]{uri,ExoConstants.PLAY_URL_NAME};
            }else {
                showToast(getString(R.string.unexpected_intent_action, paramIntent.getAction()));
                return;
            }
            MediaSource[] mediaSources = new MediaSource[uris.length];
            for (int i = 0; i < uris.length; i++) {
                mediaSources[i] = buildMediaSource(uris[i], extensions[i]);
            }
            MediaSource mediaSource = mediaSources.length == 1 ? mediaSources[0]
                    : new ConcatenatingMediaSource(mediaSources);
            boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
            if (haveResumePosition) {
                player.seekTo(resumeWindow, resumePosition);
            }
            player.prepare(mediaSource, !haveResumePosition, false);
            playerNeedsSource = false;
        }
    }

    private DrmSessionManager<FrameworkMediaCrypto> buildDrmSessionManager(UUID uuid,
                                                                           String licenseUrl, Map<String, String> keyRequestProperties) throws UnsupportedDrmException {
        if (Util.SDK_INT < 18) {
            return null;
        }
        HttpMediaDrmCallback drmCallback = new HttpMediaDrmCallback(licenseUrl,
                buildHttpDataSourceFactory(false), keyRequestProperties);
        return new DefaultDrmSessionManager<>(uuid,
                FrameworkMediaDrm.newInstance(uuid), drmCallback, null, mainHandler, eventLogger);
    }

    private void releasePlayer() {
        if (player != null) {
            shouldAutoPlay = player.getPlayWhenReady();
            updateResumePosition();
            player.release();
            player = null;
            trackSelector = null;
            eventLogger = null;
        }
    }
    private MediaSource buildMediaSource(Uri uri, String overrideExtension) {
        int type = Util.inferContentType(!TextUtils.isEmpty(overrideExtension) ? "." + overrideExtension
                : uri.getLastPathSegment());
        switch (type) {
            case C.TYPE_SS:
                return new SsMediaSource(uri, buildDataSourceFactory(false),
                        new DefaultSsChunkSource.Factory(mediaDataSourceFactory), mainHandler, eventLogger);
            case C.TYPE_DASH:
                return new DashMediaSource(uri, buildDataSourceFactory(false),
                        new DefaultDashChunkSource.Factory(mediaDataSourceFactory), mainHandler, eventLogger);
            case C.TYPE_HLS:
                return new HlsMediaSource(uri, mediaDataSourceFactory, mainHandler, eventLogger);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource(uri, mediaDataSourceFactory, new DefaultExtractorsFactory(),
                        mainHandler, eventLogger);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }
    private HttpDataSource.Factory buildHttpDataSourceFactory(boolean useBandwidthMeter) {
        return ((TLiveApplication) getApplication())
                .buildHttpDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }



    private void updateResumePosition() {
        resumeWindow = player.getCurrentWindowIndex();
        resumePosition = player.isCurrentWindowSeekable() ? Math.max(0, player.getCurrentPosition())
                : C.TIME_UNSET;
    }

    private void clearResumePosition() {
        resumeWindow = C.INDEX_UNSET;
        resumePosition = C.TIME_UNSET;
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {


    }
    private void updateProgress() {

        long duration = player == null ? 0 : player.getDuration();
        long position = player == null ? 0 : player.getCurrentPosition();
        long bufferedPosition = player == null ? 0 : player.getBufferedPosition();
        EventBus.getDefault().
                post(new MessageEvent(
                        position,
                        duration
                        ,bufferedPosition));

        int playbackState = player == null ? ExoPlayer.STATE_IDLE : player.getPlaybackState();
        if (playbackState != ExoPlayer.STATE_IDLE && playbackState != ExoPlayer.STATE_ENDED) {
            long delayMs;
            if (player.getPlayWhenReady() && playbackState == ExoPlayer.STATE_READY) {
                delayMs = 1000 - (position % 1000);
                if (delayMs < 200) {
                    delayMs += 1000;
                }
            } else {
                delayMs = 1000;
            }

            mainHandler.postDelayed(updateProgressAction,delayMs);

        }
    }
    private void updatePlayPauseButton() {
        boolean playing = player != null && player.getPlayWhenReady();
        EventBus.getDefault().post(new PlayEvent(playing));
    }
    private void updateNavigation() {
        Timeline currentTimeline = player != null ? player.getCurrentTimeline() : null;
        boolean haveNonEmptyTimeline = currentTimeline != null && !currentTimeline.isEmpty();
        boolean isSeekable = false;
        boolean enablePrevious = false;
        boolean enableNext = false;
        if (haveNonEmptyTimeline) {
            int currentWindowIndex = player.getCurrentWindowIndex();
            currentTimeline.getWindow(currentWindowIndex, currentWindow);
            isSeekable = currentWindow.isSeekable;
            enablePrevious = currentWindowIndex > 0 || isSeekable || !currentWindow.isDynamic;
            enableNext = (currentWindowIndex < currentTimeline.getWindowCount() - 1)
                    || currentWindow.isDynamic;
        }
        EventBus.getDefault().post(new NextEvent(isSeekable,enablePrevious,enableNext));
    }


    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        updatePlayPauseButton();
        updateProgress();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
        updateNavigation();
        updateProgress();
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onPlayerError(ExoPlaybackException e) {
        String errorString = null;
        if (e.type == ExoPlaybackException.TYPE_RENDERER) {
            Exception cause = e.getRendererException();
            if (cause instanceof MediaCodecRenderer.DecoderInitializationException) {
                // Special case for decoder initialization failures.
                MediaCodecRenderer.DecoderInitializationException decoderInitializationException =
                        (MediaCodecRenderer.DecoderInitializationException) cause;
                if (decoderInitializationException.decoderName == null) {
                    if (decoderInitializationException.getCause() instanceof MediaCodecUtil.DecoderQueryException) {
                        errorString = getString(R.string.error_querying_decoders);
                    } else if (decoderInitializationException.secureDecoderRequired) {
                        errorString = getString(R.string.error_no_secure_decoder,
                                decoderInitializationException.mimeType);
                    } else {
                        errorString = getString(R.string.error_no_decoder,
                                decoderInitializationException.mimeType);
                    }
                } else {
                    errorString = getString(R.string.error_instantiating_decoder,
                            decoderInitializationException.decoderName);
                }
            }
        }
        if (errorString != null) {
            showToast(errorString);
        }
//        playerNeedsSource = true;
//        if (isBehindLiveWindow(e)) {
//            clearResumePosition();
//            initializePlayer();
//        } else {
//            updateResumePosition();
//            updateButtonVisibilities();
//            showControls();
//        }
    }

    @Override
    public void onPositionDiscontinuity() {
        updateNavigation();
        updateProgress();
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
    }

    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return ((TLiveApplication) getApplication())
                .buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    private void showToast(int messageId) {
        showToast(getString(messageId));
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }


        private void showNotifiction(){

            android.support.v4.app. NotificationCompat.Builder mBuilder = new android.support.v4.app.NotificationCompat.Builder(this);


            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setComponent(new ComponentName("example.com.sunshine", "example.com.sunshine.download.Home.Main111Activity"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            PendingIntent contentIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notfication_activity);//RemoveViews所加载的布局文件
            remoteViews.setTextViewText(R.id.txt_notifyMusicName, "这是一个Test");//设置文本内容
            remoteViews.setTextColor(R.id.txt_notifyNickName, Color.parseColor("#abcdef"));//设置文本颜色
            remoteViews.setImageViewResource(R.id.img_notifyClose, R.mipmap.notify_btn_close);//设置图片
            boolean playing = player != null && player.getPlayWhenReady();
            if (playing){
                remoteViews.setImageViewResource(R.id.img_notifyPlayOrPause,R.drawable.notify_btn_light_pause2_normal_xml);
            }else{
                remoteViews.setImageViewResource(R.id.img_notifyPlayOrPause,R.drawable.notify_btn_dark_pause_normal_xml);
            }
            remoteViews.setOnClickPendingIntent(R.id.img_notifyNext,
                    PendingIntent.getBroadcast(this, 0, new Intent(ACTION_NEXT_RADIO), 0));


            remoteViews.setOnClickPendingIntent(R.id.img_notifyPlayOrPause,
                    PendingIntent.getBroadcast(this, 0, new Intent(ACTION_PLAY_ONLINE), 0));


            mBuilder.setContentTitle("WonderBuy")//设置通知栏标题
                    .setContentText("床前明月光，疑是地上霜。举头望明月，低头思故乡。--李白")
                    .setContentIntent(contentIntent)
                    .setTicker("订单详情")
                    .setWhen(System.currentTimeMillis())
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .setOngoing(false)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.notify_btn_close))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentInfo("订单详情").setContent(remoteViews);


//            mNotificationManager.notify(requestCode, mBuilder.mNotification);
            startForeground(requestCode, mBuilder.mNotification);
        }

    private void registerPlayControlReceiver() {
        unregisterPlayControlReceiver();

        mPlayControlReceiver = new HeadsetReceiver();
        IntentFilter m_IntentFilter = new IntentFilter();
        m_IntentFilter.addAction(ACTION_PLAY_ONLINE);
        m_IntentFilter.addAction(ACTION_NEXT_RADIO);
//        m_IntentFilter.addAction(ACTION_PREVIOUS_RADIO);
//        m_IntentFilter.addAction(ACTION_COLLECT_RADIO);
//        m_IntentFilter.addAction(CollectionManager.ACTION_COLLECTION_UPDATE);
//        m_IntentFilter.addAction(Intent.ACTION_SCREEN_OFF);

        registerReceiver(mPlayControlReceiver, m_IntentFilter);
    }

    private void unregisterPlayControlReceiver() {
        if (mPlayControlReceiver != null) {
            unregisterReceiver(mPlayControlReceiver);
            mPlayControlReceiver = null;
        }
    }

    private BroadcastReceiver mPlayControlReceiver = null;
    public static final String ACTION_PLAY_ONLINE = "cn.cnr.fm.action.playonline";

    public static final String ACTION_NEXT_RADIO = "cn.cnr.fm.action.next";

//    public static final String ACTION_EXIT_RADIO = "cn.cnr.fm.action.exit";
//    public static final String ACTION_UPDATEUI = "cn.cnr.fm.action.updateui";
//    public static final String ACTION_PREVIOUS_RADIO = "cn.cnr.fm.action.previous";
//    public static final String ACTION_COLLECT_RADIO = "cn.cnr.fm.action.collect";

    private class HeadsetReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            boolean playing = player != null && player.getPlayWhenReady();
            if (action.equals(ACTION_PLAY_ONLINE)) {

                if (playing) {
                   player.setPlayWhenReady(false);
                } else {
                    player.setPlayWhenReady(true);
                }
            } else if (action.equals(ACTION_NEXT_RADIO)) {
                next();
            }
            showNotifiction();
            /*else if (action.equals(ACTION_PREVIOUS_RADIO)) {
                playPreviousData();
            } else if (action.equals(ACTION_COLLECT_RADIO)) {
                doCollect();
            } else if (action.equals(CollectionManager.ACTION_COLLECTION_UPDATE)) {
                try {
                    showNotification();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/

        }
    }
}
