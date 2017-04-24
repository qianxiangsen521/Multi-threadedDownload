package example.com.sunshine.HTTP;

/**
 * Created by qianxiangsen on 2017/4/12.
 */

public class DownloadInfo {

    private int id;
    private String name;
    private String url;
    private int len;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
