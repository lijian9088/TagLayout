package com.lyz.taglayout.tag;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Locale;

/**
 * @author liyanze
 * @create 2019/03/19
 * @Describe
 */
public class TagLayout extends ViewGroup implements View.OnClickListener {

    public OnTagClickListener listener;
    private float totalWidth;
    private float totalHeight;
    private float lineWidth;
    private float lineHeight;
    private float viewWidth;
    private float viewHeight;
    private float xLineSpacing = 20f;
    private float yLineSpacing = 20f;
    private int padding = 20;

    public TagLayout(Context context) {
        this(context, null);
    }

    public TagLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPadding(padding, padding, padding, padding);
    }

    public void initTag(List<Tag> tagList) {
        for (int i = 0; i < tagList.size(); i++) {
            Tag tag = tagList.get(i);
            addTag(tag);
            tag.setOnClickListener(this);
        }
    }

    public void addTag(Tag tag) {
        if (tag != null) {
            addView(tag);
        }
    }

    public void setOnTagClickListener(OnTagClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        System.out.println("********onMeasure********");

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChildWithMargins(child,
                    widthMeasureSpec, 0,
                    heightMeasureSpec, 0);
        }

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();

            int oneWidth = (int) (width + params.leftMargin + params.rightMargin + xLineSpacing * 2);
            int oneHeight = (int) (height + params.topMargin + params.bottomMargin + yLineSpacing * 2);

            if ((totalWidth + oneWidth + getPaddingLeft() + getPaddingRight()) < widthSize) {
                //一行未满

            } else {
                //一行已满，需要换行
                lineWidth = totalWidth;
                totalWidth = 0;
                totalHeight = totalHeight + lineHeight;
                lineHeight = 0;
            }

            lineHeight = lineHeight > oneHeight ? lineHeight : oneHeight;
            totalWidth = totalWidth + oneWidth;

            viewWidth =  lineWidth + getPaddingLeft() + getPaddingRight();
            viewHeight = lineHeight + totalHeight + getPaddingTop() + getPaddingBottom();

        }

        int measuredWidth;
        int measuredHeight;

        if (widthMode == MeasureSpec.AT_MOST) {
            measuredWidth = (int) viewWidth;
        } else if (widthMode == MeasureSpec.EXACTLY) {
            measuredWidth = widthMeasureSpec;
        } else {
            measuredWidth = widthMeasureSpec;
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            measuredHeight = (int) viewHeight;
        } else if (widthMode == MeasureSpec.EXACTLY) {
            measuredHeight = heightMeasureSpec;
        } else {
            measuredHeight = heightMeasureSpec;
        }

        String format = String.format(Locale.ENGLISH, "measuredWidth:%d,measuredHeight:%d", measuredWidth, measuredHeight);
        System.out.println(format);

        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        System.out.println("********onLayout********");

        float totalWidth = 0;
        float totalHeight = 0;
        float lineHeight = 0;

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

            int oneWidth = (int) (width + params.leftMargin + params.rightMargin + xLineSpacing * 2);
            int oneHeight = (int) (height + params.topMargin + params.bottomMargin + yLineSpacing * 2);

            float v = totalWidth + oneWidth + getPaddingLeft() + getPaddingRight();
            float k = getWidth();

            System.out.println("v:" + v + ",k:" + k);

            if ((totalWidth + oneWidth + getPaddingLeft() + getPaddingRight()) <= getWidth()) {
                //一行未满
                left = (int) (totalWidth + getPaddingLeft() + params.leftMargin + xLineSpacing);
                top = (int) (totalHeight + getPaddingTop() + params.topMargin + yLineSpacing);
                right = left + width;
                bottom = top + height;

            } else {
                //一行已满，需要换行
                totalWidth = 0;
                totalHeight = totalHeight + lineHeight;
                lineHeight = 0;

                left = (int) (params.leftMargin + getPaddingLeft() + xLineSpacing);
                top = (int) (totalHeight + getPaddingLeft() + params.topMargin + yLineSpacing);
                right = left + width;
                bottom = top + height;

            }

            lineHeight = oneHeight > lineHeight ? oneHeight : lineHeight;
            totalWidth = totalWidth + oneWidth;

            child.layout(left, top, right, bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick((Tag) v);
        }
    }
}
