package com.android.sample.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yoon on 2017. 7. 14..
 */

public class DividerItemDecorator extends RecyclerView.ItemDecoration {

    private int mDividerHeight;
    private Drawable mDivider;

    public DividerItemDecorator(Context context) {
        final TypedArray a = context.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        mDivider = a.getDrawable(0);
        mDividerHeight = mDivider.getIntrinsicHeight();
        a.recycle();
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        final int lineLeft = parent.getPaddingLeft();
        final int lineRight = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int childTransitionY = Math.round(ViewCompat.getTranslationY(child));
            final int top = child.getBottom() + params.bottomMargin + childTransitionY;
            final int bottom = top + mDividerHeight;

            mDivider.setBounds(lineLeft, top, lineRight, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDividerHeight);
    }
}
