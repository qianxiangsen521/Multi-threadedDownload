package example.com.sunshine.download.request;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;

import example.com.sunshine.download.Http.SystemUtils;
import example.com.sunshine.download.Http.UUID;
import example.com.sunshine.download.Http.entity.BaseResponse;

/**
 * Created by xingzhiqiao on 16/3/24.
 */
public class HttpManger implements Response.Listener<BaseResponse>, Response.ErrorListener {


    private static HttpManger mInstance;
    private Context context;
    private HttpDataListener listener;

    public HttpManger(Context context, HttpDataListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public static HttpManger getInstance(Context context, HttpDataListener listener) {
        mInstance = new HttpManger(context, listener);
        return mInstance;
    }

    /**
     * post请求
     *
     * @param url
     * @param params
     */
    public void sendPostRequest(String url, HashMap<String, String> params, boolean isCache) {

        //先获取缓存数据
        if (isCache) {
            String json = "";

            try {
                json = StringTool.replaceEscapeSeq(new String(VolleySingleton.getInstance(context).getHttpDiskCache().get(url).data));
            } catch (Exception e) {
                e.printStackTrace();
            }

            json = StringTool.removeBom(json);
            if (!TextUtils.isEmpty(json)) {
                BaseResponse response = CnrRequest.getReponse(json, url);
                if (response != null) {
                    listener.onDataReady(response);
                }
            }

        }

        String guid = SystemUtils.getGuid();
        String project_id = SystemUtils.getMetaProjectId(context);
        String app_id = SystemUtils.getMetaAppId(context);
        // hashStr字符串用base64加密后在用md5加密
        String hashStr = guid + "_" + app_id + "_" + project_id;
        String base64Token = Base64.encodeToString(hashStr.trim().getBytes(),
                Base64.NO_WRAP);

//        UUID uid = new UUID();
//        uid.setGuid(base64Token);
//        String hash = uid.nativeGuidCal(context, uid);
        if (params == null) {
            params = new HashMap<String, String>();
        }
        params.put("project_id", project_id);
        params.put("app_id", app_id);
        params.put("GUID", guid);
//        params.put("hash", hash);
        params.put("sn", SystemUtils.getMd5UniqueID(context));
        params.put("platform", "0");//0代表 Android


        CnrRequest request = new CnrRequest(Request.Method.POST, url, params, this, this);
        VolleySingleton.getInstance(context).addToRequestQueue(request);


    }

    /**
     * Get请求
     *
     * @param url
     * @param params
     */
    public void sendGetRequest(String url, HashMap<String, String> params) {
        CnrRequest request = new CnrRequest(Request.Method.GET, url, params, this, this);
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }


    @Override
    public void onResponse(BaseResponse response) {
        listener.onDataReady(response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        CnrLog.d("Error", error.getMessage()+"");
        ErrorStatus status = null;
        if (VolleyErrorHelper.isNetworkProblem(error)) {
            status = ErrorStatus.NETWORK_EXCEPTION;
        } else if (VolleyErrorHelper.isServerProblem(error)) {
            status = ErrorStatus.ERROR_CONNECTION;
        } else if (VolleyErrorHelper.isParseError(error)) {
            status = ErrorStatus.ERROR_RESULT_PARSE;
        } else if (VolleyErrorHelper.isTimeout(error)) {
            status = ErrorStatus.ERROR_CONNECTION_TIMEOUT;
        } else if (VolleyErrorHelper.isMalformedURL(error)) {
            status = ErrorStatus.ERROR_MALFORMED_URL;
        } else {
            status = ErrorStatus.NETWORK_EXCEPTION;
        }
        listener.onError(status);
    }
}
