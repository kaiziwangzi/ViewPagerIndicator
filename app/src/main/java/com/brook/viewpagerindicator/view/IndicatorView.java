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

    private int mRadiusPx = DensityUtils.dp2px(getContext(), DEFAULT_RADIUS_DP);
    private int mPaddingPx = DensityUtils.dp2px(getContext(), DEFAULT_PADDING_DP);
    private int mSelectedPosition;
    private Paint mPaint;

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
