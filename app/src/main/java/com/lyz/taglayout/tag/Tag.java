package com.lyz.taglayout.tag;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author liyanze
 * @create 2019/03/19
 * @Describe
 */
public class Tag extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float textSize = 50f;
    private int textColor = Color.BLACK;
    private String text;

    public Tag(Context context) {
        this(context, null);
    }

    public Tag(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Tag(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        invalidate();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        invalidate();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawText(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = resolveSize(200, widthMeasureSpec);
        int height = resolveSize(50, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private void drawText(Canvas canvas) {
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(textColor);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float offset = (fontMetrics.ascent + fontMetrics.descent) / 2f;

        if (!TextUtils.isEmpty(text)) {
            canvas.drawText(text, getCenterX(), getCenterY() - offset, paint);
        }
    }

    private float getCenterX() {
        return getWidth() / 2f;
    }

    private float getCenterY() {
        return getHeight() / 2f;
    }
}
