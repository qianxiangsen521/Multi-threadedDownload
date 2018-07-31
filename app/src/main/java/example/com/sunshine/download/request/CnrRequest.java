package example.com.sunshine.download.request;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.com.sunshine.download.Application.TLiveApplication;
import example.com.sunshine.download.Http.Configuration;
import example.com.sunshine.download.Http.entity.BaseResponse;
import example.com.sunshine.download.Http.entity.RadioInfo;


public class CnrRequest<T> extends JsonRequest<BaseResponse<T>> {


    /**
     * 表单类型(使用POST方式提交时,需指定请求头的Content-Type)
     */
    private static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";


    public CnrRequest(int method, String url, Map<String, String> parameters, Response.Listener<BaseResponse<T>> listener, Response.ErrorListener errorListener) {
        super(method, url, buildRequestBody(parameters), listener, errorListener);
    }


    @Override
    protected Response parseNetworkResponse(NetworkResponse networkResponse) {
        String json = "";
        try {
            json = StringTool.replaceEscapeSeq(new String(networkResponse.data, Constants.DEFAULT_CONTENT_CHARSET));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //需要去掉BOM头
        json = StringTool.removeBom(json);
        CnrLog.d("params", json);

        if (TextUtils.isEmpty(json)) {
            return Response.error(new ParseError());
        }

        BaseResponse response = getReponse(json, getUrl());
        return Response.success(response, HttpHeaderParser.parseCacheHeaders(networkResponse));

    }


    public static BaseResponse getReponse(String json, String url) {

        final Class<? extends BaseResponse> responseClass = HttpMapSingleTon.getInstance().getHttpResponseType(url);


//        if (url.startsWith(Configuration.HOME_COMMON_MORE)) {//首页更多
//            ArrayList<CommonHomeMoreAndCategoryInfo> commonListInfo = JsonDataFactory.getHomeCommonList(json);
//            HomeMoreResponse moreResponse = new HomeMoreResponse();
//            moreResponse.setCommonListInfo(commonListInfo);
//            return moreResponse;
//        }

        if (url.equals(Configuration.HOME_HEADER_RUL)) {//首页banner图
            List<RadioInfo> notes = JsonDataFactory.getRadioInfoList(json);
            HomeTopResponse topResponse = new HomeTopResponse();
            topResponse.setRadioInfos(notes);
            return topResponse;
        }
        if (url.equals(Configuration.HOME_BOTTOM_URL)) {

            List<CategoryRadioInfo> categoryRadioInfos = JsonDataFactory.getHomeCategoryList(json);
            HomeBottomResponse bottomResponse = new HomeBottomResponse();
            bottomResponse.setCategoryRadioInfos(categoryRadioInfos);
            return bottomResponse;
        }
        if (url.equals(Configuration.CATEGORY_INDEX)) {
            ArrayList<CategoryInfoList> categoryList = JsonDataFactory.parseCategoryIndex(json);
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setCategoryList(categoryList);
            return categoryResponse;
        }
        BaseResponse response = new BaseResponse();
        try {
            response = new Gson().fromJson(json, responseClass);
        } catch (Exception e) {
            e.printStackTrace();
            CnrLog.d("Error", e.toString());
        }
        return response;
    }


    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }


    /**
     * 构建参数请求体(只接收String和int)
     *
     * @param parameters
     * @returnß
     */
    protected static String buildRequestBody(Map<String, String> parameters) {
        StringBuilder sb = new StringBuilder();
        final ArrayList<String> keyList = new ArrayList<>(parameters.keySet());
        final int keyListLength = keyList.size();

        for (int i = 0; i < keyListLength; i++) {
            final String key = keyList.get(i);
            Object value = parameters.get(key);
            if (value == null) {
                continue;
            }
            if (value instanceof String || value instanceof Integer) {
                sb.append(URLEncode(key));
                sb.append("=");

                sb.append(URLEncode(String.valueOf(value)));
                sb.append("&");
            }
        }
        // Volley使用HttpGet实现GET请求,HttpGet会在url后面追加参数,所以要保留最后一个&
        // int length=sb.length();
        // if(length>0){
        // sb.deleteCharAt(length-1);
        // }
        return sb.toString();
    }

    public static String URLEncode(String str) {
        String encodedStr = "";
        try {
            encodedStr = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedStr;
    }

    @Override
    public String getBodyContentType() {
        return FORM_CONTENT_TYPE;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        // headers.put("t", "");
        return headers;
    }
}
