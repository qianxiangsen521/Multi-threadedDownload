package example.com.sunshine.download.request;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {
	private static final int BUFFER_SIZE = 8 * 1024; // 8 KB

	public static void copyStream(InputStream is, OutputStream os)
			throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		while (true) {
			int count = is.read(bytes, 0, BUFFER_SIZE);
			if (count == -1) {
				break;
			}
			os.write(bytes, 0, count);
		}
	}

	public static void byteToMd5(InputStream is) throws IOException {
		byte[] bytes = new byte[is.available()];
		while (true) {
			int count = is.read(bytes, 0, is.available());
			if (count == -1) {
				break;
			}
		}
	}

	/**
	 * 关闭输入输出流
	 * 
	 * @param closeable
	 */
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException exp) {
				exp.printStackTrace();
			}
		}
	}
}
