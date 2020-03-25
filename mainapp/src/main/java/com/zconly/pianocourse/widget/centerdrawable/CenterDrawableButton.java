package com.zconly.pianocourse.widget.centerdrawable;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.CompoundButton;

public class CenterDrawableButton extends CompoundButton {
    public CenterDrawableButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CenterDrawableButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CenterDrawableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CenterDrawableButton(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        CenterDrawableHelper.preDraw(this, canvas);
        super.onDraw(canvas);
    }
}
