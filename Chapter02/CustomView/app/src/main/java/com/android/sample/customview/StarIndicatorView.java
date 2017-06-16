package com.android.sample.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by yoon on 2017. 6. 16..
 */

public class StarIndicatorView extends LinearLayout {

    private ImageView mStar1ImageView;
    private ImageView mStar2ImageView;
    private ImageView mStar3ImageView;
    private int mSelected;

    public StarIndicatorView(Context context) {
        super(context);
        initializeViews(context, null);
    }

    public StarIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }

    public StarIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StarIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeViews(context, attrs);
    }

    private void initializeViews(Context context, AttributeSet attributeSet) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.layout_star_indicator, this);
        if (attributeSet != null) {
            TypedArray a = context.obtainStyledAttributes(attributeSet,
                    R.styleable.StarIndicatorView);
            mSelected = a.getInteger(0, 0);
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mStar1ImageView = (ImageView) findViewById(R.id.star1);
        mStar2ImageView = (ImageView) findViewById(R.id.star2);
        mStar3ImageView = (ImageView) findViewById(R.id.star3);
        setSelected(mSelected, true);
    }

    public void setSelected(int selected) {
        setSelected(selected, false);
    }

    private void setSelected(int selected, boolean force) {
        if (force || mSelected != selected) {
            if (2 > mSelected && mSelected < 0) {
                return;
            }
            mSelected = selected;
            if (mSelected == 0) {
                mStar1ImageView.setImageResource(R.drawable.ic_star_yellow_500_24dp);
                mStar2ImageView.setImageResource(R.drawable.ic_star_yellow_200_24dp);
                mStar3ImageView.setImageResource(R.drawable.ic_star_yellow_200_24dp);
            } else if (mSelected == 1) {
                mStar1ImageView.setImageResource(R.drawable.ic_star_yellow_200_24dp);
                mStar2ImageView.setImageResource(R.drawable.ic_star_yellow_500_24dp);
                mStar3ImageView.setImageResource(R.drawable.ic_star_yellow_200_24dp);
            } else if (mSelected == 2) {
                mStar1ImageView.setImageResource(R.drawable.ic_star_yellow_200_24dp);
                mStar2ImageView.setImageResource(R.drawable.ic_star_yellow_200_24dp);
                mStar3ImageView.setImageResource(R.drawable.ic_star_yellow_500_24dp);
            }
        }
    }

    public int getSelected() {
        return mSelected;
    }
}
