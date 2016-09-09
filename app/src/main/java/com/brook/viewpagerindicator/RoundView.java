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
    private int mDefaultRadius = 10;
    private int mSpaceEach = 5;
    private int mCount = 6;
    private float mFactor = 1.0f;
    private float mSmallRadius = 4;
    private float mBigRadius = 10;
    private float mBaseRadius;
    private RoundAnimation roundAnimation;
    private float[] floats;
    private int mode = MODE_LEFT_EXTEND;

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
        roundAnimation.setDuration(1000);
        roundAnimation.setAnimationListener(this);
        floats = new float[mCount];
        mBaseRadius = (mBigRadius - mSmallRadius) / mCount;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mCount; i++) {
            floats[i] = mFactor;
            if (floats[i] <= i + 1) {
                floats[i] = i + 1;
            }
        }

        for (int i = 0; i < mCount; i++) {
            if (mode == MODE_LEFT_EXTEND || mode == MODE_LEFT_NARROW) {
                canvas.drawCircle((mDefaultRadius + mSpaceEach) * i + (i + 1) * mDefaultRadius * 2,
                        mDefaultRadius, mSmallRadius + mBaseRadius * floats[i], mPaint);
            } else if (mode == MODE_RIGHT_NARROW || mode == MODE_RIGHT_EXTEND) {
                canvas.drawCircle((mDefaultRadius + mSpaceEach) * i + (i + 1) * mDefaultRadius * 2,
                        mDefaultRadius, mSmallRadius + mBaseRadius * floats[mCount - 1 - i], mPaint);
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
