package com.lyz.taglayoutlib;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author liyanze
 * @create 2019/03/19
 * @Describe
 */
public class TagLayout extends ViewGroup implements View.OnClickListener {

    public OnTagClickListener listener;
    //    private float totalWidth;
//    private float totalHeight;
//    private float lineWidth;
//    private float lineHeight;
    //    private float viewWidth;
//    private float viewHeight;
    private float xLineSpacing = 20f;
    private float yLineSpacing = 20f;
    private int padding = 20;
    private ArrayList<Line> lines;

    public TagLayout(Context context) {
        this(context, null);
    }

    public TagLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPadding(padding, padding, padding, padding);
        lines = new ArrayList<>();
    }

    public void initTag(List<Tag> tagList) {
        removeAllViews();
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

        float viewWidth = 0;
        float viewHeight = 0;
        float totalWidth = 0;
        float totalHeight = 0;
        float lineWidth = 0;
        float lineHeight = 0;

        lines.clear();
        Line line = null;

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

            if (line == null) {
                line = new Line();
                lines.add(line);
            }

            if ((totalWidth + oneWidth + getPaddingLeft() + getPaddingRight()) <= widthSize) {
                //一行未满
                left = (int) (totalWidth + getPaddingLeft() + params.leftMargin + xLineSpacing);
                top = (int) (totalHeight + getPaddingTop() + params.topMargin + yLineSpacing);
                right = left + width;
                bottom = top + height;

                Line.Child l = new Line.Child();
                l.view = child;
                l.left = left;
                l.top = top;
                l.right = right;
                l.bottom = bottom;
                line.addChild(l);

                System.out.println("000" + l);

            } else {
                //一行已满，需要换行
                lineWidth = totalWidth;
                totalWidth = 0;
                totalHeight = totalHeight + lineHeight;
                lineHeight = 0;

                left = (int) (params.leftMargin + getPaddingLeft() + xLineSpacing);
                top = (int) (totalHeight + getPaddingLeft() + params.topMargin + yLineSpacing);
                right = left + width;
                bottom = top + height;

                Line.Child l = new Line.Child();
                l.view = child;
                l.left = left;
                l.top = top;
                l.right = right;
                l.bottom = bottom;
                line = new Line();
                line.addChild(l);
                lines.add(line);

                System.out.println("111" + l);
            }

            lineHeight = lineHeight > oneHeight ? lineHeight : oneHeight;
            totalWidth = totalWidth + oneWidth;

            viewWidth = lineWidth + getPaddingLeft() + getPaddingRight();
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

            lineHeight = lineHeight > oneHeight ? lineHeight : oneHeight;
            totalWidth = totalWidth + oneWidth;

            child.layout(left, top, right, bottom);
        }

        //使用Line，仅支持width为match_parent
//        for (int i = 0; i < lines.size(); i++) {
//            Line line = lines.get(i);
//            List<Line.Child> children = line.getChildren();
//            for (int j = 0; j < children.size(); j++) {
//                Line.Child child = children.get(j);
//                child.view.layout(child.left, child.top, child.right, child.bottom);
//            }
//        }
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
