package example.com.sunshine.download.request;

/**
 * 自定义Log,可以控制是否打印
 * Created by xingzhiqiao on 16/2/22.
 */
public class CnrLog {

    /**
     * 是否打印日志
     */
    private static boolean isPrint = false;

    public static final String APPNAME = "cnrFM";

    private CnrLog() {
    }

    public static void setPrintable(boolean isPrint) {
        CnrLog.isPrint = isPrint;
    }

    private static String createLog() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(APPNAME);
        buffer.append("]");
        return buffer.toString();
    }

    /**
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int v(String tag, String msg) {
        return isPrint ? android.util.Log.v(createLog() + tag, msg) : -1;
    }

    /**
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int d(String tag, String msg) {
        return isPrint ? android.util.Log.d(createLog() + tag, msg) : -1;
    }

    /**
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int i(String tag, String msg) {
        return isPrint ? android.util.Log.i(createLog() + tag, msg) : -1;
    }

    /**
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int w(String tag, String msg) {
        return isPrint ? android.util.Log.w(createLog() + tag, msg) : -1;
    }

    /**
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int e(String tag, String msg) {
        return isPrint ? android.util.Log.e(createLog() + tag, msg) : -1;
    }
}
