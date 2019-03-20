package com.lyz.taglayout.tag;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author liyanze
 * @create 2019/03/19
 * @Describe
 */
public class TagLayout extends ViewGroup {

    float totalWidth = 0;
    float totalHeight = 0;
    float maxHeight = 0;

    float xLineSpacing = 20f;
    float yLineSpacing = 20f;

    public TagLayout(Context context) {
        this(context, null);
    }

    public TagLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addTab(Tag tag) {
        if (tag != null) {
            addView(tag);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChildWithMargins(child,
                    widthMeasureSpec, 0,
                    heightMeasureSpec, 0);
        }

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();

            int left = 0;
            int top = 0;
            int right = 0;
            int bottom = 0;

            int oneWidth = width + params.leftMargin + params.rightMargin;
            int oneHeight = height + params.topMargin + params.bottomMargin;

            if ((totalWidth + oneWidth) < getWidth()) {
                //一行未满
                left = (int) (totalWidth + params.leftMargin);
                top = (int) (totalHeight + params.topMargin);
                right = left + width;
                bottom = top + height;
            } else {
                //一行已满，需要换行
                totalWidth = 0;
                totalHeight = totalHeight + maxHeight;
                maxHeight = 0;

                left = params.leftMargin;
                top = (int) totalHeight + params.topMargin;
                right = left + width;
                bottom = top + height;
            }

            maxHeight = oneHeight > maxHeight ? oneHeight : maxHeight;
            totalWidth = totalWidth + oneWidth;

            child.layout(left, top, right, bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
