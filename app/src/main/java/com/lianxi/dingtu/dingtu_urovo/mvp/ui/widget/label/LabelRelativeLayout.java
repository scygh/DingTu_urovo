package com.lianxi.dingtu.dingtu_urovo.mvp.ui.widget.label;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class LabelRelativeLayout extends RelativeLayout {
    private LabelViewHelper mLabelViewHelper;
    private boolean mLabelVisable = true;
    public LabelRelativeLayout(Context context) {
        this(context, null);
    }

    public LabelRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLabelViewHelper = new LabelViewHelper(context, attrs);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mLabelVisable) {
            mLabelViewHelper.drawLabel(this, canvas);
        }
    }

    public void setTextContent(String content) {
        mLabelViewHelper.setTextContent(content);
        invalidate();
    }

    public void setTextTitle(String title) {
        mLabelViewHelper.setTextTitle(title);
        invalidate();
    }

    public void setLabelBackGroundColor(int color) {
        mLabelViewHelper.setLabelBackGroundColor(color);
        invalidate();
    }

    public void setLabelVisable(boolean visable) {
        mLabelVisable = visable;
        postInvalidate();
    }
}
