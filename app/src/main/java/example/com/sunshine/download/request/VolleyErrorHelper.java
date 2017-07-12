package example.com.sunshine.download.request;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.net.MalformedURLException;

import example.com.sunshine.R;

/**
 * 统一处理{@link VolleyError}异常
 * @author xingzhiqiao
 * @time 2016-3-25 下午02:26:36
 */
public class VolleyErrorHelper
{
	/** 网络正常 */
	public final static String HTTP_CODE_200 = "200";

	/**
	 * http 响应成功
	 */
	public static boolean isReponseSucess(String httpCode) {
		return HTTP_CODE_200.equals(httpCode);
	}

	/**
	 * 获取http失败原因
	 */
	public static String getHttpErrorReason(Context context, String httpCode) {
		try {
			int id = context.getResources()
					.getIdentifier("http_code_" + httpCode, "string",
							context.getPackageName());
			return context.getString(id);
		} catch (Exception e) {
			return context.getString(R.string.request_failed);
		}
	}

	/**
	* Returns appropriate message which is to be displayed to the user 
	* against the specified error object.
	* 
	* @param error
	* @param context
	* @return
	*/
	public static String getMessage(Object error, Context context) {
		if (isTimeout(error)) {
			return context.getResources().getString(R.string.request_timeout);
		} else if (isServerProblem(error)) {
			return handleServerError(error, context);
		} else if (isNetworkProblem(error)) {
			return context.getResources().getString(R.string.network_exception);
		}
		return context.getResources().getString(R.string.generic_error);
	}

	/**
	* Determines whether the error is related to network
	* @param error
	* @return
	*/
	public static boolean isNetworkProblem(Object error) {
		return (error instanceof NetworkError)
				|| (error instanceof NoConnectionError);
	}

	/**
	* Determines whether the error is related to server
	* @param error
	* @return
	*/
	public static boolean isServerProblem(Object error) {
		return (error instanceof ServerError)
				|| (error instanceof AuthFailureError);
	}

	/**
	 * Determines whether the error is timeout
	 *
	 * @param error
	 * @return
	 */
	public static boolean isTimeout(Object error) {
		return error instanceof TimeoutError;
	}
	
	/**
	 * Determines whether the error is malformed URL
	 *
	 * @param error
	 * @return
	 */
	public static boolean isMalformedURL(Object error) {
		if (error instanceof VolleyError) {
			VolleyError vollyError=(VolleyError) error;
			Throwable cause = vollyError.getCause();
			if(cause==null){
				return false;
			}
			cause = cause.getCause();
			if (cause instanceof MalformedURLException) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Determines whether the error is ParseError
	 *
	 * @param error
	 * @return
	 */
	public static boolean isParseError(Object error) {
		return error instanceof ParseError;
	}
	
	/**
	* Handles the server error, tries to determine whether to show a stock message or to 
	* show a message retrieved from the server.
	* 
	* @param err
	* @param context
	* @return
	*/
	private static String handleServerError(Object err, Context context) {
		VolleyError error = (VolleyError) err;
		NetworkResponse response = error.networkResponse;

		if (response != null) {
			try {
				int id = context.getResources().getIdentifier(
						"http_code_" + response.statusCode, "string",
						context.getPackageName());
				return context.getString(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return context.getResources().getString(R.string.generic_error);
	}
}
