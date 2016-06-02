package com.kar.gameoflife.uitls;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Karthik on 5/31/2016.
 */
public class UIUtils {

    public static int convertDip2Pixels(Context context, int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }

}
