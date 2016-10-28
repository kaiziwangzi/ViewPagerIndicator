package com.brook.viewpagerindicator.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

/**
 * Created by Brook on 2016/10/28.
 */
public class DensityUtils {

    public static int dp2px(@NonNull Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

        return px;
    }
}
