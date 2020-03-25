package com.zconly.pianocourse.widget.centerdrawable;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class CenterDrawableTextView extends AppCompatTextView {

    public CenterDrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CenterDrawableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CenterDrawableTextView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        CenterDrawableHelper.preDraw(this, canvas);
        super.onDraw(canvas);
    }
}
