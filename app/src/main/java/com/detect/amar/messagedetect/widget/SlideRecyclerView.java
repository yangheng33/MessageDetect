package com.detect.amar.messagedetect.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.detect.amar.messagedetect.R;

/**
 * Created by SAM on 2015/8/5.
 */
public class SlideRecyclerView extends RecyclerView {

    private SlideView mFocusedItemView;
    private int slideViewLayoutResId;
    private int slideWidth;
    private int rowViewLayoutResId;

    public SlideRecyclerView(Context context) {
        super(context);
    }

    public SlideRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(context, attrs);
    }

    public SlideRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setAttributes(context, attrs);
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.slideView);
        slideViewLayoutResId = typedArray.getResourceId(R.styleable.slideView_slideLayoutResId, 0);
        slideWidth = typedArray.getDimensionPixelSize(R.styleable.slideView_slideWidth, 60);
        rowViewLayoutResId = typedArray.getResourceId(R.styleable.slideView_rowViewLayoutResId,0);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                float x = event.getX();
                float y = event.getY();
                View view = findChildViewUnder(x, y);
                if (view != null) {
                    mFocusedItemView = (SlideView) view;
                }
            }
        }
        if (mFocusedItemView != null) {
            mFocusedItemView.onRequireTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    public int getSlideViewLayoutResId() {
        return slideViewLayoutResId;
    }

    public int getSlideWidth() {
        return slideWidth;
    }

    public int getRowViewLayoutResId(){
        return rowViewLayoutResId;
    }
}
