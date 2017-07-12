package example.com.sunshine.download.request;

/**
 * 接口请求状态
 *
 *@author xingzhiqiao
 *@date 2016-3-25
 */
public enum ErrorStatus
{
    OK(9527, "接口请求成功"),
    NETWORK_EXCEPTION(OK.getCode() + 1, "网络异常"),
    ERROR_MALFORMED_URL(NETWORK_EXCEPTION.getCode() + 1, "无效的url"),
    ERROR_CONNECTION(ERROR_MALFORMED_URL.getCode() + 1, "服务器连接错误"),
    ERROR_CONNECTION_TIMEOUT(ERROR_CONNECTION.getCode() + 1, "服务器连接超时"),
    ERROR_RESULT_PARSE(ERROR_CONNECTION_TIMEOUT.getCode() + 1, "结果解析错误"),
    REQUEST_FAIL(ERROR_RESULT_PARSE.getCode() + 1, "接口请求失败"),
    USER_CANCEL_REQUEST(REQUEST_FAIL.getCode() + 1, "用户取消请求");
    
    private int code = 0;
    private String msg = "";
    
    ErrorStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getMessage() {
        return msg;
    }

}
