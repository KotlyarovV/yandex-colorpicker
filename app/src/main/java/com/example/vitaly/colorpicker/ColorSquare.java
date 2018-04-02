package com.example.vitaly.colorpicker;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatImageHelper;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by vitaly on 02.04.18.
 */

public class ColorSquare extends AppCompatImageView {

    private int centerX;

    public int getCenterX() {
        return centerX;
    }

    public ColorSquare(Context context, int centerX) {
        super(context, null);
        this.centerX = centerX;
    }

    public ColorSquare(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorSquare(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
