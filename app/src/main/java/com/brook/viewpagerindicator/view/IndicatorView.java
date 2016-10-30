package com.brook.viewpagerindicator.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.brook.viewpagerindicator.R;
import com.brook.viewpagerindicator.util.DensityUtils;

/**
 * Created by Brook on 2016/10/28.
 */
public class IndicatorView extends View {
    private static final int DEFAULT_RADIUS_DP = 5;
    private static final int DEFAULT_PADDING_DP = 10;
    private static final int DEFAULT_SELECTED_COLOR = 0xfffffff;
    private static final int DEFAULT_UNSELECTED_COLOR = 0x33ffffff;

    private int mRadiusPx = DensityUtils.dp2px(getContext(), DEFAULT_RADIUS_DP);
    private int mPaddingPx = DensityUtils.dp2px(getContext(), DEFAULT_PADDING_DP);
    private int mSelectedPosition;
    private Paint mPaint;
    private int count = 8;

    public IndicatorView(Context context) {
        super(context);
        init();
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        int diameterPx = mRadiusPx * 2;
        int desiredWidth = 0;
        int desiredHeight = diameterPx;
        if (0 != count) {
            desiredWidth = (count * diameterPx) + (mPaddingPx * (count - 1));

        }
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(widthSize, desiredWidth);
        } else {
            width = desiredWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(heightSize, desiredHeight);
        } else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawIndicatorView(canvas);
    }

    private void drawIndicatorView(Canvas canvas) {
        int y = getCalculateHeight() / 2;
        for (int i = 0; i < count; i++) {
            canvas.drawCircle(getPositionX(i), y, mRadiusPx, mPaint);
        }
    }

    private int getPositionX(int position) {
        return mRadiusPx + (mRadiusPx * 2 + mPaddingPx) * position;
    }

    private int getCalculateWidth() {
        return 0;
    }

    private int getCalculateHeight() {
        return getHeight();
    }
}
