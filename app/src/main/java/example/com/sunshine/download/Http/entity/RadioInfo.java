package example.com.sunshine.download.Http.entity;

import android.graphics.drawable.Drawable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

import example.com.sunshine.download.Http.Configuration;

public class RadioInfo  implements MultiItemEntity {
    private int id;

    private String name;

    private String currentPlay;

    private String recommed;

    private String introduction;

    private String currentPlayTime;

    private String imgUrl;

    private Drawable testImg;// 测试用的

    private String broadCaster;

    private String playLength;

    // 1-直播音频，2-电视直播,2-点播，3-专辑
    private String type;

    private String radioBillUrl;// 直播节目单地址

    private String praiseUrl;

    private String enjoyUrl;

    private String moreEnjoyUrl;

    private String aid;

    private int recommend_pos_id;

    private String downloadUrl;

    private String albumListUrl;// 专辑节目单地址

    private String albumIntroductionUrl;

    private String albumDetailImgUrl;

    private ArrayList<PlayUrlItem> playUrlList;

    private ArrayList<PlayUrlItem> albumUrlList;

    private String playCount;

    private int programId;

    // 1就是代表收藏 0就是未收藏
    private boolean collect;

    private String albums_url;

    private int albumId;

    private String programIntro;

    private int pay_status;

    private int cms_album_id;

    /**
     * 上次播放时间
     */
    private long lastPlayTime;

    public int Type;

    private ArrayList<RadioInfo> playList;

    // 1就是代表收藏 0就是未收藏
    // public String getPlaycount() {
    // return playcount;
    // }
    //
    // public void setPlaycount(String playcount) {
    // this.playcount = playcount;
    // }

    public RadioInfo(){

    }
    public RadioInfo(int type){
        Type = type;
    }
    private int is_fee;// 是否是收费节目

    private int fee;// 收费价格

    private int auto_model;// 音频介绍图片显示模式

    private String album_name;

    private String album_id;


    public String getAlbum_name() {
        return album_name;
    }
    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public int getAuto_model() {
        return auto_model;
    }

    public void setAuto_model(int auto_model) {
        this.auto_model = auto_model;
    }

    public String getProgram_intro() {
        return programIntro;
    }

    public void setProgram_intro(String intro) {
        this.programIntro = intro;
    }

    public boolean isCollect() {
        return collect;
    }

    public String getAlbums_url() {
        return albums_url;
    }

    public void setAlbums_url(String albums_url) {
        this.albums_url = albums_url;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public String getAlbumDetailImgUrl() {
        return albumDetailImgUrl;
    }

    public void setAlbumDetailImgUrl(String albumDetailImgUrl) {
        this.albumDetailImgUrl = albumDetailImgUrl;
    }

    public String getAlbumListUrl() {
        return albumListUrl;
    }

    public void setAlbumListUrl(String albumListUrl) {
        this.albumListUrl = albumListUrl;
    }

    public String getAlbumIntroductionUrl() {
        return albumIntroductionUrl;
    }

    public void setAlbumIntroductionUrl(String albumIntroductionUrl) {
        this.albumIntroductionUrl = albumIntroductionUrl;
    }

    public ArrayList<PlayUrlItem> getPlayUrlList() {
        return playUrlList;
    }

    public ArrayList<PlayUrlItem> getAlbumUrlList() {
        return albumUrlList;
    }

    public void setPlayUrlList(ArrayList<PlayUrlItem> playUrlList) {
        this.playUrlList = playUrlList;
    }

    public void setAlbumUrlList(ArrayList<PlayUrlItem> playUrlList) {
        this.albumUrlList = playUrlList;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentPlay() {
        return currentPlay;
    }

    public void setCurrentPlay(String currentPlay) {
        this.currentPlay = currentPlay;
    }

    public String getRecommed() {
        return recommed;
    }

    public void setRecommed(String recommed) {
        this.recommed = recommed;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCurrentPlayTime() {
        return currentPlayTime;
    }

    public void setCurrentPlayTime(String currentPlayTime) {
        this.currentPlayTime = currentPlayTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Drawable getTestImg() {// 测试方法
        return testImg;
    }

    public void setTestImg(Drawable testImg) {// 测试方法
        this.testImg = testImg;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBroadCaster() {
        return broadCaster;
    }

    public void setBroadCaster(String broadCaster) {
        this.broadCaster = broadCaster;
    }

    public String getPlayLength() {
        return playLength;
    }

    public void setPlayLength(String playLength) {
        this.playLength = playLength;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRadioBillUrl() {
        return radioBillUrl;
    }

    public void setRadioBillUrl(String radioBillUrl) {
        this.radioBillUrl = radioBillUrl;
    }

    public String getPraiseUrl() {
        return praiseUrl;
    }

    public void setPraiseUrl(String praiseUrl) {
        this.praiseUrl = praiseUrl;
    }

    public String getEnjoyUrl() {
        return enjoyUrl;
    }

    public void setEnjoyUrl(String enjoyUrl) {
        this.enjoyUrl = enjoyUrl;
    }

    public String getMoreEnjoyUrl() {
        return moreEnjoyUrl;
    }

    public void setMoreEnjoyUrl(String moreEnjoyUrl) {
        this.moreEnjoyUrl = moreEnjoyUrl;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playcount) {
        this.playCount = playcount;
    }

    public String getProgramIntro() {
        return programIntro;
    }

    public void setProgramIntro(String programIntro) {
        this.programIntro = programIntro;
    }

    public int getIs_fee() {
        return is_fee;
    }

    public void setIs_fee(int is_fee) {
        this.is_fee = is_fee;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public int getCms_album_id() {
        return cms_album_id;
    }

    public void setCms_album_id(int cms_album_id) {
        this.cms_album_id = cms_album_id;
    }

    /**
     * 判断是否需要缴费提示
     *
     * @return
     */
    public boolean isNeedFee() {
        return pay_status == Configuration.CODE_FEE_NEED_PAY
                || pay_status == Configuration.CODE_FEE_OUT_DATE;
    }

    public int getRecommend_pos_id() {
        return recommend_pos_id;
    }

    public void setRecommend_pos_id(int recommend_pos_id) {
        this.recommend_pos_id = recommend_pos_id;
    }


    public long getLastPlayTime() {
        return lastPlayTime;
    }

    public void setLastPlayTime(long lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }


    public ArrayList<RadioInfo> getPlayList() {
        return playList;
    }

    public void setPlayList(ArrayList<RadioInfo> playList) {
        this.playList = playList;
    }


    @Override
    public int getItemType() {
        return PROGRAM_TYPE_BANNER;
    }
    public static final int PROGRAM_TYPE_THEATRE = 2;
    public static final int PROGRAM_TYPE_ARTIST = 1;
    public static final int PROGRAM_TYPE_BANNER = 0;
}
