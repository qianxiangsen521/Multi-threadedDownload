package example.com.sunshine.download;

public class SqlString {

    public static String Int(long number){
        return "'"+number+"'";
    }

    public static String String(String name){
        return "'"+name+"'";
    }
}