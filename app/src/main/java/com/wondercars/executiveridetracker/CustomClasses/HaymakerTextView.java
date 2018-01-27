package com.wondercars.executiveridetracker.CustomClasses;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by acer on 17/11/17.
 */

public class HaymakerTextView extends android.support.v7.widget.AppCompatTextView {
    public HaymakerTextView(Context context) {
        super(context);
        init();
    }

    public HaymakerTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HaymakerTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/haymaker.ttf");
            setTypeface(tf);
    }
}
