package example.com.sunshine.download.request;



import org.apache.http.protocol.HTTP;
/**
 * 常量
 * Created by xingzhiqiao on 16/2/29.
 */
public class Constants {


    /**
     * 默认的编码方式
     */
    public static final String DEFAULT_CONTENT_CHARSET = HTTP.UTF_8;


    /**
     * 文件后缀
     * 包括(jpg,png)
     */
    public enum FileSuffix {
        JPG(".jpg"), PNG(".png"), APK(".apk");

        String suffix;

        FileSuffix(String suffix) {
            this.suffix = suffix;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        @Override
        public String toString() {
            return suffix;
        }
    }

}
