package com.lyz.taglayout.tag;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.lyz.taglayout.R;

/**
 * @author liyanze
 * @create 2019/03/19
 * @Describe
 */
public class Tag extends android.support.v7.widget.AppCompatTextView {

    public int position;
    private float textSize = 16f;
    private int padding = 10;

    private Tag(Context context) {
        this(context, null);
    }

    private Tag(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private Tag(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundDrawable(getResources().getDrawable(R.drawable.tag_selector));
        setGravity(TEXT_ALIGNMENT_CENTER);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        setTextColor(Color.BLACK);
        setPadding(padding, padding, padding, padding);
    }

    public static Tag newTag(Context context, int position) {
        Tag tag = new Tag(context);
        tag.position = position;
        return tag;
    }

}
