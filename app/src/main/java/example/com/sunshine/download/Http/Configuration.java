package example.com.sunshine.download.Http;

public class Configuration {
    // 项目ID（APPID）
    public static final String PROJECT_ID = "1";


    public static final String YOUDAO_KEY = "6e7ee56e0e9ca81313b19c04f9a1320c";

    public static final int HOME_HEADER_FLAG = 99;

    // public static final int HOME_CENTER_FLAG = 98;
    // public static final int HOME_CENTER_FLAG_1 = 198;
    public static final int HOME_BOTTOM_FLAG = 97;

    public static final int SEARCH_HOT_WORD_FLAG = 96;

    public static final int SEARCH_SEARCH_RESULT_FLAG = 95;

    public static final int HOME_MORE_FLAG = 94;

    public static final int DETAIL_GUESS_LIKE_FLAG = 93;

    public static final int GUESS_LIKE_MORE_FLAG = 92;

    public static final int HOME_PROGRAM_LIST_YESTERDAY_FLAG = 91;

    public static final int DETAIL_WELL_FLAG = 90;

    public static final int ALBUM_CHOOSE_FLAG = 89;

    public static final int ALBUM_CHOOSE_CLICK_FLAG = 88;

    public static final int CATEGORY_LIVE_CHOOSE = 87;

    public static final int CATEGORY_LIVE_SELECT_FAV = 86;

    public static final int DETALI_PLAYER_COLLECT_FLAG = 85;

    public static final int PLAYER_FAV_FLAG = 84;

    public static final int COMMON_MORE_AND_CATEGORY_FLAG = 83;

    public static final int ALBUM_PROGRAM_BILL_FLAG = 82;

    public static final int CATEGORY_ACTIVITY_RETCODE = 81;

    public static final int CATEGORY_MORE_ACTIVITY_REQUEST_CODE = 80;

    public static final int PLAYER_ALBUM_INTRODUCTION_FLAG = 79;

    public static final int PLAYER_ALBUM_CURRENT_PLAY_FLAG = 78;

    public static final int PLAYER_BACK_LISTENE_FLAG = 77;

    public static final int ALBUM_PROGRAM_NEW_BILL_FLAG = 76;

    public static final int CATEGORY_HOME_PARSER = 1001;

    public static final int CATGORY_COMM_LIVE_STATUS = 1002;

    public static final int ALBUM_STATUS = 1003;

    public static final int ALBUM_DETAIL_LIST_STATUS = 1004;

    public static final int RANKING_INDEX_STATUS = 1005;

    public static final int ALL_RANKING_STATUS = 1006;

    public static final int ACTIVATE_STATUS = 1007;

    public static final int CATEGORY_LIVE_PROVINCE_CHOOSE_STATUS = 1008;

    public static final int CATEGORY_LIVE_PROVINCE_STATUS = 1009;

    public static final int CATGORY_COMM_LIVE_PRO_STATUS = 1010;

    public static final int CATEGORY_DIANBO_PRO_STSTUS = 1011;

    public static final int FEEDBACK_PARSER = 1012;

    public static final int VERSION_UPDATE_PARSER = 1013;

    public static final int FAV_PARSER = 1014;

    public static final int FAV_PARSER_ALL = 1015;

    public static final int FAV_PARSER_PROGRAM = 1016;

    public static final int FAV_PARSER_ALBUM = 1017;

    public static final int FAV_PARSER_DEL = 1018;

    public static final int RANKING_INDEX_FAV = 1019;

    public static final int FAV_ADD_REQUEST = 1020;

    public static final int FAV_CANCEL_REQUEST = 1021;

    public static final int HITSHOW_PARSER = 1022;

    public static final int HITSHOW_DETAIL_PARSER = 1023;

    public static final int FAV_ITEM_DEL = 1024;

    public static final int CATEGORY_LIVE_LEFT_CITY_REQUEST = 1025;

    public static final int CATEGORY_LIVE_RIGHT_CHANNEL_REQUEST = 1026;

    public static final int CATEGORY_REQUEST_MORE_LIST = 1027;

    public static final int CATEGORY_REQUEST_MORE_DETAIL_INTRODUCTION = 1028;

    public static final int CATEGORY_REQUEST_MORE_DETAIL_LIST = 1029;

    public static final int CATEGORY_REQUEST_MORE_DETAIL_LIST_NEW = 1030;

    public static final int CATEGORY_REQUEST_MORE_LIST_NEW = 1031;

    public static final int CATEGORY_REQUEST_MORE_DETAIL_ORDER = 1032;

    public static final int CATEGORY_PROVINCE_LIST_REQUEST = 1033;

    public static final int CATEGORY_CHANNEL_LIST_REQUEST = 1034;

    public static final int HOME_BOTTOM_ALBUMS_URL_FLAG = 1035;

    public static final int ALBUMS_ULR_FLAG = 1036;

    public static final int CATEGORY_REQUEST_ALBUM_ITEM_PLAY_URL_FLAG = 1037;

    public static final int SEARCH_RESULT_GET_PLAY_URL_FLAG = 1038;

    public static final int DETAIL_PLAYER_GET_ALBUM_PLAY_URL_FLAG = 1039;

    public static final int MORE_APP_URL_FLAG = 1040;

    public static final int SPLASH_APP_URL_FLAG = 1041;

    public static final int CEREMONY_APP_URL_FLAG = 1042;

    public static final int PAY_ALBUM = 1043;

    public static final int ORDER_LIST = 1044;

    public static final int DISMIESS_DIALOG = 1055;

    public static final int FEE_LIST_MORE = 1056;

    public static final int RADIO_INTRO_INFO = 1057;

    public static final int ADD_PLAYCOUNT = 1058;

    public static final int SEARCH_SINGLE = 1059;

    public static final int YOUDAO_SDK = 1060;
    public static final boolean TEST = false;

    // public static final boolean CHECEK = true;
    // public static final String host = TEST ?
    // "http://fmapi.test.sunshinefm.cn/"
    // : "http://fmapi.sunshinefm.cn/";
    // TODO 切换到正式服务器改过来
    // fmapi.ck.test.cnrmobile.com 验收版测试服务器
    // http://fmapi.ck.cnrmobile.com/ 验收版正式服务器
    public static final String host = TEST ? "http://fmapi.test.sunshinefm.cn/"
            : "http://fmapi.sunshinefm.cn/";
    public static final String CATEGORY_INDEX = host + "recommend/catemain";

    public static final String HOME_COMMON_MORE = host + "recommend/bumindex";

    public static final String CATEGORY_PROVINCE_LIVE = host
            + "api/Client/category_shengshitai";

    public static final String CATEGORY_LIVE = host + "liveArea";

    public static final String RANKING_INDEX = host + "recommend/ranklist";

    public static final String HITSHOW_INDEX = host + "recommend/hotcate";

    public static final String RANKING_FAV_STATUS = host + "isfav/getfavflag";

    public static final String RANKING_FAV_REQUES_ADD = host
            + "favorite/collect";

    public static final String RANKING_FAV_REQUES_CANCEL = host
            + "favorite/cancel";

    // public static final String CATEGORY_LIVE_LOCATION =
    // "http://mobileclient.test.cnrmobile.com/api/Client/category_location";
    public static final String CATEGORY_LIVE_LOCATION = host + "liveArea";

    public static final String CATEGORY_DIANBO_CLASSIFICATION = host
            + "api/Client/category_dianbo_Classification";

    public static final String ALBUM = host
            + "api/Client/category_dianbo_zhuanji";

    public static final String ALL_RANKING = host + "api/Client/all_ranking";

    public static final String ACTIVATE_URL = host + "user/activate";

    // 首页焦点图
    public static final String HOME_HEADER_RUL = host + "found/top";

    // 首页其他
    public static final String HOME_BOTTOM_URL = host + "recommend/";

    public static final String SEARCH_HOT_WORD_URL = host + "recommend/shotkey";

    //有道sdk
    public static final String YOU_DAO = "http://gorgon.youdao.com/gorgon/request.s";

    public static final String SEARCH_SEARCH_RESULT_URL = host + "search/index";

    public static final String GUESS_LIKE_URL = host + "recommend/guesslike";

    public static final String GUESS_LIKE_MORE_URL = host
            + "recommend/guessmore";

    public static final String FAV_RESULT_URL = host + "favorite/collect";

    public static final String FAV_LIST_URL = host + "favlist/getFavlist";

    public static final String FAV_RESULT_DEL = host + "favorite/cancel";

    // 意见反馈
    public static final String FEEDBACK_URL = host + "comment/docomment";

    //意见
    public static final String FEEDBACK = host + "comment/addcomment";
    // 更多应用
    public static final String MOREAPP_URL = host + "recommend/applist";

    public static final String VERSION_UPDATE = host + "recommend/upgrade";

    // 首页节目单
    public static final String HOME_PROGRAM_LIST_URL = host
            + "programlist/getplist";

    // 末级页点赞
    public static final String DETAIL_WELL_RUL = host + "favorite/praise";

    // 分类筛选
    public static final String ALBUM_CHOOSE_URL = host + "recommend/classific";

    // 首页推广图片
    public static final String SPLASH_ENERALIZE_URL = host + "system/config";

    public static final String PAY_ALBUM_URL = host + "recommend/purchase";

    // 分享接口
    public static final String CEREMONY_SHARE_URL = host
            + "activity/activityShare";

    // 我的订购
    public static final String ORDER_LIST_URL = host + "myorder/orderlist";

    // 收费专题更多
    public static final String FEE_LIST_MORE_URL = host
            + "recommend/recommposinfo";

    //关键词搜索列表 下午 2:13
    public static final String SEARCH_HOT_ALL_RESULT = host + "search/multiResult";
    public static final String SEARCH_KEYWORD_LIST = host + "search/keywordList";
    public static final String SEARCH_PLAY_ELEMENT = host + "search/playElement";
    public static final String SEARCH_SINGLE_RESULT = host + "search/singleResult";
    public static final int SEARCH_PLAY_ELEMENT_CODE = 1101;
    public static final int SEARCH_KEYWORD_CODE = 1100;
    public static final int SEARCH_HOT_ALL_CODE = 1102;

    /**
     * 音频介绍信息
     */
    public static final String RADIO_INTRO_INFO_URL = host + "found/showtype";

    /**
     * 播放次数加一
     */
    public static final String ADD_PLAYCOUNT_URL = host + "search/playElement";

    public static String DefLocata = "北京";

    public static final String TYPE_ALBUM = "4"; // 专辑

    public static final String TYPE_ALBUM_PROGRAM = "5"; // 专辑单条节目

    public static final String TYPE_RADIO_LIVE = "6"; // 电台直播

    public static final String TYPE_OUT_LINK = "8";// 纯外链

    public static final int TYPE_FEE = 1;// 收费

    public static int playDuration = 0;// 文件总时长

    public static int dest = 0;// 当前播放seekbar的positon

    public static int position = 0;// 播放文件的position

    public static String ALBUM_GET_URL = host
            + "recommend/getAlbumListByAlbumId?albumId=";

    /**
     * 收费类型
     */
    public static final int CODE_FEE_NEED_PAY = 1001;// 您即将收听的节目为收费节目，若要继续收听，请交费

    public static final int CODE_FEE_OUT_DATE = 1003;// 您以超过购买有效期，如要继续收听，请交费

    public static final int CODE_FEE_HAVE_PAY = 1000;// 已交费，没过期

    public static final int CODE_PAY_SUCCEDD = 2000;

    /**
     * 友盟分享
     */
    public static final String DESCRIPTOR = "com.umeng.share";

    private static final String TIPS = "请移步官方网站 ";

    private static final String END_TIPS = ", 查看相关说明.";

    public static final String TENCENT_OPEN_URL = TIPS
            + "http://wiki.connect.qq.com/android_sdk使用说明" + END_TIPS;

    public static final String PERMISSION_URL = TIPS
            + "http://wiki.connect.qq.com/openapi权限申请" + END_TIPS;

    public static final String SOCIAL_LINK = "http://www.umeng.com/social";

    public static final String SOCIAL_TITLE = "友盟社会化组件帮助应用快速整合分享功能";

    public static final String SOCIAL_IMAGE = "http://www.umeng.com/images/pic/banner_module_social.png";

    public static final String SOCIAL_CONTENT = "友盟社会化组件（SDK）让移动应用快速整合社交分享功能，我们简化了社交平台的接入，为开发者提供坚实的基础服务：（一）支持各大主流社交平台，"
            + "（二）支持图片、文字、gif动图、音频、视频；@好友，关注官方微博等功能"
            + "（三）提供详尽的后台用户社交行为分析。http://www.umeng.com/social";
}
