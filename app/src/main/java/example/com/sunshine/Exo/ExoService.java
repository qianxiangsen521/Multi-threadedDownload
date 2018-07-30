package example.com.sunshine.Exo;

import android.app.ActivityManager;
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
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import example.com.sunshine.Exo.E.MessageEvent;
import example.com.sunshine.Exo.E.NextEvent;
import example.com.sunshine.Exo.E.PlayEvent;
import example.com.sunshine.IRemoteService;
import example.com.sunshine.IRemoteServiceCallback;
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


    final RemoteCallbackList<IRemoteServiceCallback> mCallbacks
            = new RemoteCallbackList<>();



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (IRemoteService.class.getName().equals(intent.getAction())) {
            return mBinder;
        }
        return null;
    }

    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        @Override
        public void registerCallback(IRemoteServiceCallback cb) throws RemoteException {
            if (cb != null){
                mCallbacks.register(cb);
            }
        }

        @Override
        public void unregisterCallback(IRemoteServiceCallback cb) throws RemoteException {
            if (cb != null){
                mCallbacks.unregister(cb);
            }
        }
    };
    private void playStateChangedisPlay(boolean isPlay) {

        final int N = mCallbacks.beginBroadcast();
        for (int i = 0; i < N; i++) {
            try {
                mCallbacks.getBroadcastItem(i).PlayEvent(isPlay);
            } catch (RemoteException e) {
            }
        }
        mCallbacks.finishBroadcast();
    }
    private void playStateChangedMessage(long mCurrentPosition,long mDuration,long mBufferedPosition) {

        final int N = mCallbacks.beginBroadcast();
        for (int i = 0; i < N; i++) {
            try {
                mCallbacks.getBroadcastItem(i).MessageEvent(mCurrentPosition,mDuration,mBufferedPosition);
            } catch (RemoteException e) {
            }
        }
        mCallbacks.finishBroadcast();
    }
    private void playStateChangedNext(boolean isSeekable,boolean enablePrevious,boolean enableNext) {

        final int N = mCallbacks.beginBroadcast();
        for (int i = 0; i < N; i++) {
            try {
                mCallbacks.getBroadcastItem(i).NextEvent(isSeekable,enablePrevious,enableNext);
            } catch (RemoteException e) {
            }
        }
        mCallbacks.finishBroadcast();
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

        releasePlayer();

        mainHandler.removeCallbacks(updateProgressAction);
        stopForeground(true);

        unregisterPlayControlReceiver();
        mNotificationManager.cancel(R.string.remote_service_started);

        mCallbacks.kill();

        if (mHandler != null) {
            mHandler.removeMessages(REPORT_PLAY);
            mHandler.removeMessages(REPORT_MESSAGE);
            mHandler.removeMessages(REPORT_NEXT);
        }
        mHandler = null;
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
        if (TextUtils.isEmpty(str1))
            return;
        if (str1.equals(ExoConstants.ACTION_SEEK)){
            seekTo(paramIntent.getLongExtra(ExoConstants.PLAY_POSTITION,0));
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
            if (playerNeedsSource) {
                Uri[] uris;
                String[] extensions;
                if (ExoConstants.ACTION_PLAY.equals(paramIntent.getAction())) {
                    String uri = paramIntent.getStringExtra(ExoConstants.PLAY_URL);
                    uris = new Uri[]{Uri.parse(uri)};
                    extensions = new String[]{uri};


                }else if (ExoConstants.ACTION_VIEW_LIST.equals(paramIntent.getAction())) {
                    String[] uriStrings = paramIntent.getStringArrayExtra(ExoConstants.URI_LIST_EXTRA);
                    uris = new Uri[uriStrings.length];
                    for (int i = 0; i < uriStrings.length; i++) {
                        uris[i] = Uri.parse(uriStrings[i]);
                    }
                    extensions = paramIntent.getStringArrayExtra(ExoConstants.EXTENSION_LIST_EXTRA);
                    if (extensions == null) {
                        extensions = new String[uriStrings.length];

                    }
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

        showNotifiction();

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


        Message message = Message.obtain();
        message.what = REPORT_MESSAGE;
        message.obj = new MessageEvent(position,
                duration
                ,bufferedPosition);
        mHandler.dispatchMessage(message);


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
        Message message = Message.obtain();
        message.what = REPORT_PLAY;
        message.obj = new PlayEvent(playing);
        mHandler.dispatchMessage(message);
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

        Message message = Message.obtain();
        message.what = REPORT_NEXT;
        message.obj = new NextEvent(isSeekable,enablePrevious,enableNext);
        mHandler.dispatchMessage(message);


    }


    private static final int REPORT_PLAY= 1;

    private static final int REPORT_MESSAGE= 2;

    private static final int REPORT_NEXT= 3;

    private Handler mHandler = new Handler() {
        @Override public void handleMessage(Message msg) {
            switch (msg.what) {
                case REPORT_PLAY:

                    PlayEvent playEvent = (PlayEvent) msg.obj;
                    playStateChangedisPlay(playEvent.isPlayWhenReady());
                    break;
                case REPORT_MESSAGE:
                    MessageEvent messageEvent =(MessageEvent) msg.obj;
                    playStateChangedMessage( messageEvent.getmCurrentPosition(),
                            messageEvent.getmDuration()
                            ,messageEvent.getmBufferedPosition());
                    break;
                case REPORT_NEXT:
                    NextEvent nextEvent =(NextEvent) msg.obj;
                    playStateChangedNext(nextEvent.isSeekable(),nextEvent.isEnablePrevious(),
                            nextEvent.isEnableNext());
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

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
        if (player == null){
            return;
        }
        android.support.v7.app. NotificationCompat.Builder mBuilder = new android.support.v7.app.NotificationCompat.Builder(this);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName("example.com.sunshine", "example.com.sunshine.download.Home.Main111Activity"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        PendingIntent contentIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notfication_activity);//RemoveViews所加载的布局文件
        remoteViews.setTextViewText(R.id.txt_notifyMusicName, "这是一个Test");//设置文本内容
        remoteViews.setTextColor(R.id.txt_notifyNickName, Color.parseColor("#abcdef"));//设置文本颜色
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

        remoteViews.setOnClickPendingIntent(R.id.content_notfication,
                PendingIntent.getBroadcast(this,0,new Intent(ACTION_PLAY_CONTENT),0));

        remoteViews.setImageViewResource(R.id.exit, R.mipmap.notify_btn_close);
        remoteViews.setOnClickPendingIntent(R.id.exit,
                PendingIntent.getBroadcast(this, 0, new Intent(ACTION_EXIT_RADIO), 0));

        mBuilder.setContentTitle("WonderBuy")//设置通知栏标题
                .setContentText("床前明月光，疑是地上霜。举头望明月，低头思故乡。--李白")
                .setContentIntent(contentIntent)
//                .setTicker("订单详情")
//                .setWhen(System.currentTimeMillis())
//                .setPriority(Notification.PRIORITY_DEFAULT)
//                .setAutoCancel(true)
//                .setOngoing(false)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.notify_btn_close))
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
        m_IntentFilter.addAction(ACTION_EXIT_RADIO);
        m_IntentFilter.addAction(ACTION_PLAY_CONTENT);

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

    public static final String ACTION_EXIT_RADIO = "cn.cnr.fm.action.exit";

    public static final String ACTION_PLAY_CONTENT = "cn.cnr.fm.action.content";

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
            } else if (action.equals(ACTION_PLAY_CONTENT)){
                PlayManager.onRestartApp(context);
            }
            showNotifiction();

        }
    }
}
