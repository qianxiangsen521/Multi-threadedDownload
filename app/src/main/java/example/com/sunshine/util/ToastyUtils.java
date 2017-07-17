package example.com.sunshine.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import example.com.sunshine.R;

/**
 * Created by hywel on 2017/7/14.
 * ToastyUtils----The unusual toast
 */

public class ToastyUtils {

    private Context context;
    private static ToastyUtils toastyUtils;

    public ToastyUtils(Context context) {
        this.context = context;
        resetToasty();
        initToasty(context);
    }

    public static ToastyUtils getInstance(Context context) {
        if (toastyUtils == null) {
            toastyUtils = new ToastyUtils(context);
        }
        return toastyUtils;
    }

    private void initToasty(Context context) {
        Toasty.Config.getInstance()
                .setErrorColor(context.getResources().getColor(R.color.cnr_main_color)) // optional
                .setInfoColor(context.getResources().getColor(R.color.background_tab_pressed)) // optional
                .setSuccessColor(context.getResources().getColor(R.color.toasty_success)) // optional
                .setWarningColor(context.getResources().getColor(R.color.tab_text_color_pressed)) // optional
                .setTextColor(context.getResources().getColor(R.color.white)) // optional
                .setToastTypeface(Typeface.DEFAULT)
                .setTextSize((int) context.getResources().getDimension(R.dimen.topic_item_margin)) // optional
                .apply(); // required
    }


    public void resetToasty() {
        Toasty.Config.reset();
    }

    /**
     * 错误 Toasty
     *
     * @param text 信息
     */
    public void showErrorToasty(String text) {
        Toasty.error(context, text, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 成功 Toasty
     *
     * @param text 信息
     */
    public void showSuccessToasty(String text) {
        Toasty.success(context, text, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 状态 Toasty
     *
     * @param text 信息
     */
    public void showInfoToasty(String text) {
        Toasty.info(context, text, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 警告 Toasty
     *
     * @param text 信息
     */
    public void showWarnToasty(String text) {
        Toasty.warning(context, text, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 常规 Toasty
     *
     * @param text 信息
     */
    public void showNormalToasty(String text) {
        Toasty.normal(context, text, Toast.LENGTH_SHORT).show();
    }

}
