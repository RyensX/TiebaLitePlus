package com.huanchengfly.tieba.post.components.dividers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huanchengfly.tieba.post.R;
import com.huanchengfly.tieba.post.adapters.SearchForumAdapter;
import com.huanchengfly.tieba.post.ui.theme.interfaces.Tintable;
import com.huanchengfly.tieba.post.ui.theme.utils.ThemeUtils;
import com.huanchengfly.tieba.post.utils.DisplayUtil;

public class SearchDivider extends RecyclerView.ItemDecoration implements Tintable {
    public static final String TAG = SearchDivider.class.getSimpleName();

    private Drawable mDivider;
    private final int mOrientation;
    private final int mDividerHeight;
    private final int mCommonDividerHeight;

    public SearchDivider(Context context) {
        mOrientation = LinearLayoutManager.VERTICAL;
        mDivider = ContextCompat.getDrawable(context, R.drawable.drawable_divider);
        mDividerHeight = DisplayUtil.dp2px(context, 8);
        mCommonDividerHeight = DisplayUtil.dp2px(context, 0);
        tint();
    }

    private boolean isHeader(RecyclerView parent, View view) {
        return parent.getChildViewHolder(view).getItemViewType() == SearchForumAdapter.TYPE_EXACT;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getAdapter() != null && parent.getChildLayoutPosition(view) + 1 == parent.getAdapter().getItemCount()) {
            outRect.set(0, 0, 0, 0);
        } else if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, isHeader(parent, view) ? mDividerHeight : mCommonDividerHeight);
        } else {
            outRect.set(0, 0, isHeader(parent, view) ? mDividerHeight : mCommonDividerHeight, 0);
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent, state);
        } else {
            drawHorizontal(c, parent, state);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            if (mDivider != null) {
                final int bottom = top + (isHeader(parent, child) ? mDividerHeight : mCommonDividerHeight);
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            if (mDivider != null) {
                final int right = left + (isHeader(parent, child) ? mDividerHeight : mCommonDividerHeight);
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
        }
    }

    @Override
    public void tint() {
        mDivider = ThemeUtils.tintDrawable(mDivider, Color.TRANSPARENT);
    }
}
