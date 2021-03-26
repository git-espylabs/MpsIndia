package com.espy.mps.ui.customviews;


import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

import androidx.core.content.ContextCompat;

import com.espy.mps.R;

public class CustomTypeFace extends TypefaceSpan {

    private final Typeface newType;
    private static Context context;


    public CustomTypeFace(String family, Typeface type, Context context) {
        super(family);
        newType = type;
        this.context = context;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        applyCustomTypeFace(ds, newType);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        applyCustomTypeFace(paint, newType);
    }

    private static void applyCustomTypeFace(Paint paint, Typeface tf) {
        int oldStyle;
        Typeface old = paint.getTypeface();
        if (old == null) {
            oldStyle = 0;
        } else {
            oldStyle = old.getStyle();
        }

        int fake = oldStyle & ~tf.getStyle();
        if ((fake & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fake & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(tf);
        int color = ContextCompat.getColor(context, R.color.theme_color_main);
        paint.setColor(color);
    }
}