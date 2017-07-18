package example.com.sunshine.download.Http;

public class Configuration {

    public static final int ALBUMS_ULR_FLAG = 1036;
    public static final int HOME_BOTTOM_FLAG = 97;
    public static final int CODE_FEE_NEED_PAY = 1001;// 您即将收听的节目为收费节目，若要继续收听，请交费

    public static final int CODE_FEE_OUT_DATE = 1003;// 您以超过购买有效期，如要继续收听，请交费


    public static final String host = "http://fmapi.test.sunshinefm.cn/";

    public static final String CATEGORY_INDEX = host + "recommend/catemain";

    // 首页焦点图
    public static final String HOME_HEADER_RUL = host + "found/top";

    // 首页其他
    public static final String HOME_BOTTOM_URL = host + "recommend/";


}
