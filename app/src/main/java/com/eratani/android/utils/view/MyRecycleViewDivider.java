package com.eratani.android.utils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecycleViewDivider extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private Drawable mDivider;
    private int mDividerHeight = 2;//分割线高度，默认为1px
    private int mOrientation;//列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private boolean drawLastOne = false;
    private boolean hasFooterView = false;
    int lastPosition;

    /**
     * 默认分割线：高度为2px，颜色为灰色
     *
     * @param context
     * @param orientation 列表方向
     */
    public MyRecycleViewDivider(Context context, int orientation) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        mOrientation = orientation;

        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();

        mDividerHeight = mDivider.getIntrinsicHeight();
    }

    /**
     * 自定义分割线
     *
     * @param context
     * @param orientation 列表方向
     * @param drawableId  分割线图片
     */
    public MyRecycleViewDivider(Context context, int orientation, int drawableId) {
        this(context, orientation);
        mDivider = ContextCompat.getDrawable(context, drawableId);
        mDividerHeight = mDivider == null ? 0 : mDivider.getIntrinsicHeight();
    }

    /**
     * 自定义分割线
     *
     * @param context
     * @param orientation   列表方向
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线颜色
     */
    public MyRecycleViewDivider(Context context, int orientation, int dividerHeight, @ColorInt int dividerColor) {
        this(context, orientation);
        mDivider = null;
        mDividerHeight = dividerHeight;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }


    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mDivider == null && mPaint == null) {
            outRect.set(0, 0, 0, 0);
            return;
        }

        int lastPosition = state.getItemCount() - 1;
        this.lastPosition = lastPosition;
        int pos = parent.getChildAdapterPosition(view);

        if (hasFooterView) {
            if (pos == lastPosition) {
                outRect.set(0, 0, 0, 0);
                return;
            }

            lastPosition--;
        }

        int lastDividerHeight = drawLastOne || pos < lastPosition ? mDividerHeight : 0;
        if (this.mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, lastDividerHeight);
        } else {
            outRect.set(0, 0, lastDividerHeight, 0);
        }
    }

    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    //绘制横向 item 分割线
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = hasFooterView ? parent.getChildCount() - 1 : parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mDividerHeight;

            if (i == childSize - 1) {
                int pos = parent.getChildAdapterPosition(child);
                int lastPosition = this.lastPosition;
                if (hasFooterView) {
                    if (pos == lastPosition) break;
                    lastPosition--;
                }

                int lastDividerHeight = drawLastOne || pos < lastPosition ? mDividerHeight : 0;
                if (lastDividerHeight == 0) break;

                bottom = top + lastDividerHeight;
            }

            draw(canvas, left, top, right, bottom);
        }
    }

    //绘制纵向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = hasFooterView ? parent.getChildCount() - 1 : parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            int right = left + mDividerHeight;

            if (i == childSize - 1) {
                int pos = parent.getChildAdapterPosition(child);
                int lastPosition = this.lastPosition;
                if (hasFooterView) {
                    if (pos == lastPosition) break;
                    lastPosition--;
                }

                int lastDividerHeight = drawLastOne || pos < lastPosition ? mDividerHeight : 0;
                if (lastDividerHeight == 0) break;

                right = left + lastDividerHeight;
            }
            draw(canvas, left, top, right, bottom);
        }
    }

    private void draw(Canvas canvas, int left, int top, int right, int bottom) {
        if (mDivider != null) {
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        if (mPaint != null) {
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    public boolean isDrawLastDivider() {
        return drawLastOne;
    }

    public void setDrawLastDivider(boolean drawLastOne) {
        this.drawLastOne = drawLastOne;
    }

    public void setHasFooterView(boolean hasFooterView) {
        this.hasFooterView = hasFooterView;
    }
}
