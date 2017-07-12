package example.com.sunshine.download.Utils;

import android.graphics.Typeface;

public class Font {
	private static Typeface font;

	public static Typeface getFont() {
		return font;
	}

	public static void setFont(Typeface font) {
		Font.font = font;
	}

}
