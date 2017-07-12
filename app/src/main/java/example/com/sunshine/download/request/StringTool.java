package example.com.sunshine.download.request;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import example.com.sunshine.download.Application.TLiveApplication;

/**
 * 字符串工具类
 *
 * @author xingzhiqiao 2015-11-4
 */
public class StringTool {

    private static Context context = TLiveApplication.getInstance();

    public static String getString(int resId) {
        return TLiveApplication.getInstance().getResources().getString(resId);
    }

    /**
     * 获得屏幕分辨率widthPixels*heightPixels
     */
    public static String getScreenDisplay(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        String display = dm.widthPixels + "*" + dm.heightPixels;
        return display;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取uuid号
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * 删掉字符串中的不可见字符
     *
     * @param str
     * @return
     */
    public static String replaceEscapeSeq(String str) {
        // valid ones are \b \t \n \f \r \" \' \\
        // 非默认转义字符,\都要写两次
        str = str.replaceAll("\\s*|\t|\r|\n", "");
        return str;
    }

    /**
     * 去掉字符串的BOM头，防止解析json出错
     */
    public static String removeBom(String json) {
        if (!TextUtils.isEmpty(json)) {
            char c = json.charAt(0);
            if (c == 65279) {
                json = json.substring(1);
            }
        }
        return json;
    }

    /**
     * 把一个输入流读成字符串
     *
     * @param is
     * @param encoding
     * @return
     */
    public static String streamToString(InputStream is, String encoding) {
        StringBuilder sb = new StringBuilder();
        InputStreamReader isr = null;
        BufferedReader bufferedreader = null;
        String str = null;
        try {
            isr = new InputStreamReader(is, encoding);
            bufferedreader = new BufferedReader(isr, 4096);
            while ((str = bufferedreader.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedreader != null) {
                    bufferedreader.close();// 关闭顺序从外向内
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    /**
     * 判断list是否有效
     */
    public static <T> boolean isListValidate(List<T> list) {
        return list != null && list.size() > 0;
    }

    /**
     * 判断set是否有效
     */
    public static <T> boolean isSetValidate(HashSet<T> set) {
        return set != null && set.size() > 0;
    }

    /**
     * 数组转为列表
     */
    public static ArrayList<String> arrayToList(String[] strs) {
        if (strs == null) {
            return null;
        }
        ArrayList<String> list = new ArrayList<String>();
        for (String str : strs) {
            list.add(str);
        }
        return list;

    }

    public static String trimNull(String text) {
        if (TextUtils.isEmpty(text)) {
            return "";
        } else {
            return text.trim();
        }
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }
}
