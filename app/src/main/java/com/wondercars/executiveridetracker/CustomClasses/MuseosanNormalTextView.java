package com.wondercars.executiveridetracker.CustomClasses;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by acer on 17/11/17.
 */

public class MuseosanNormalTextView extends android.support.v7.widget.AppCompatTextView {
    public MuseosanNormalTextView(Context context) {
        super(context);
        init();
    }

    public MuseosanNormalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MuseosanNormalTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/MUSEOSANSROUNDED100.OTF");
        setTypeface(tf);
    }
}
