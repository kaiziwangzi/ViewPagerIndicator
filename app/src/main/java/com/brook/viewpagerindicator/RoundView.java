package com.brook.viewpagerindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Brook on 16/9/8.
 * Description:
 */
public class RoundView extends View implements Animation.AnimationListener {

    private final int MODE_LEFT_EXTEND = 0;
    private final int MODE_RIGHT_NARROW = 1;
    private final int MODE_LEFT_NARROW = 2;
    private final int MODE_RIGHT_EXTEND = 3;
    private Paint mPaint;
    private int mSpaceEach = 15;
    private int mCount = 7;
    private float mFactor = 1.0f;
    private float mSmallRadius = 8;
    private float mBigRadius = 15;
    private float mBaseRadius;
    private RoundAnimation roundAnimation;
    private float[] mFactors;
    private int mode = MODE_LEFT_EXTEND;
    private float mStartX, mStartY;

    public RoundView(Context context) {
        this(context, null);
    }

    public RoundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xffff4081);
        mPaint.setStyle(Paint.Style.FILL);
        roundAnimation = new RoundAnimation();
        roundAnimation.setDuration(600);
        roundAnimation.setAnimationListener(this);
        mFactors = new float[mCount];
        mBaseRadius = (mBigRadius - mSmallRadius) / mCount;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartX = (w - mBigRadius * 2 * mCount - mSpaceEach * (mCount - 1)) / 2;
        mStartY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setFactors();
        for (int i = 0; i < mCount; i++) {
            switch (mode) {
                case MODE_LEFT_EXTEND:
                case MODE_LEFT_NARROW:
                    canvas.drawCircle(mStartX + mBigRadius + i * (2 * mBigRadius + mSpaceEach),
                            mStartY, mSmallRadius + mBaseRadius * mFactors[i], mPaint);
                    break;
                case MODE_RIGHT_NARROW:
                case MODE_RIGHT_EXTEND:
                    canvas.drawCircle(mStartX + mBigRadius + i * (2 * mBigRadius + mSpaceEach),
                            mStartY, mSmallRadius + mBaseRadius * mFactors[mCount - 1 - i], mPaint);
                    break;
            }
        }
    }

    private void setFactors() {
        for (int i = 0; i < mCount; i++) {
            mFactors[i] = mFactor;
            if (mFactors[i] <= i + 1) {
                mFactors[i] = i + 1;
            }
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        switch (mode) {
            case MODE_LEFT_EXTEND:
                mode = MODE_RIGHT_NARROW;
                break;
            case MODE_RIGHT_NARROW:
                mode = MODE_RIGHT_EXTEND;
                break;
            case MODE_RIGHT_EXTEND:
                mode = MODE_LEFT_NARROW;
                break;
            case MODE_LEFT_NARROW:
                mode = MODE_LEFT_EXTEND;
                break;
        }
        startAnimation(roundAnimation);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public class RoundAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime == 1.0) {
                if (mode == MODE_LEFT_EXTEND || mode == MODE_RIGHT_EXTEND) {
                    mFactor = mCount * interpolatedTime;
                } else if (mode == MODE_RIGHT_NARROW || mode == MODE_LEFT_NARROW) {
                    mFactor = 1;
                }
            } else {
                if (mCount * interpolatedTime >= 1) {
                    if (mode == MODE_LEFT_EXTEND || mode == MODE_RIGHT_EXTEND) {
                        mFactor = mCount * interpolatedTime;
                    } else if (mode == MODE_RIGHT_NARROW || mode == MODE_LEFT_NARROW) {
                        mFactor = mCount - mCount * interpolatedTime;
                    }
                }
            }
            postInvalidate();
        }
    }

    public void notifyRound() {
        startAnimation(roundAnimation);
    }
}
