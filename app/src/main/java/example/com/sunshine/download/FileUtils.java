package example.com.sunshine.download;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class FileUtils {

    private static final String DOWNLOAD_PATH = "/download/";

    public static File setupFile(String destUri) {
        File outFile = new File(destUri);
        if (!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }
        return outFile;
    }


    public static boolean deleteFile(String filePath) {
        try {
            File mFile = new File(filePath);
            if (mFile.exists()) {
                mFile.delete();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public static String getAlbumStorageDir(Context context) {
        if (isExternalStorageWritable() | isExternalStorageReadable()){
            String  file  =context.getExternalFilesDir(
                    Environment.DIRECTORY_PICTURES)+DOWNLOAD_PATH;
            return file;
        }
        return null;
    }
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
