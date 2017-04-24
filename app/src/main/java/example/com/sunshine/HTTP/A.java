package example.com.sunshine.HTTP;

import java.text.SimpleDateFormat;
import java.util.Date;
class A {
  public static boolean getDate(String str, String str2){
    SimpleDateFormat formatter =
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    boolean isBefore = false;
    Date date1;
    Date date2;
    try {
      date1 = formatter.parse(str);
      date2 = formatter.parse(str);
      isBefore = date1.after(date2);
    } catch (Exception e) {
      e.getMessage();
    }
    return isBefore;
  }
}