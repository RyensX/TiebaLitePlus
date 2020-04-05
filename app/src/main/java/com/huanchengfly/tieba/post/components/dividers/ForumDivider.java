package com.huanchengfly.tieba.post.components.dividers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huanchengfly.theme.interfaces.Tintable;
import com.huanchengfly.theme.utils.ThemeUtils;
import com.huanchengfly.tieba.post.R;
import com.huanchengfly.tieba.post.adapters.ForumAdapter;
import com.huanchengfly.tieba.post.utils.DisplayUtil;

public class ForumDivider extends RecyclerView.ItemDecoration implements Tintable {
    public static final String TAG = "ForumDivider";

    private Drawable mDivider;
    private int mOrientation;
    private int mDividerHeight;
    private Context mContext;

    public ForumDivider(Context context, int orientation) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        mContext = context;
        mOrientation = orientation;
        mDivider = ContextCompat.getDrawable(context, R.drawable.drawable_divider);
        mDividerHeight = DisplayUtil.dp2px(context, 8);
        tint();
    }

    private boolean isHeader(int type) {
        return (type == ForumAdapter.TYPE_THREAD_COMMON || type == ForumAdapter.TYPE_THREAD_MULTI_PIC || type == ForumAdapter.TYPE_THREAD_SINGLE_PIC || type == ForumAdapter.TYPE_THREAD_VIDEO);
    }

    private boolean needDivider(RecyclerView parent, View view) {
        try {
            ForumAdapter forumAdapter = (ForumAdapter) parent.getAdapter();
            int position = parent.getChildViewHolder(view).getAdapterPosition();
            if (position >= 0) {
                if (parent.getChildViewHolder(view).getItemViewType() == ForumAdapter.TYPE_THREAD_TOP && forumAdapter.getItemViewType(position + 1) != ForumAdapter.TYPE_THREAD_TOP) {
                    return true;
                }
            }
        } catch (Exception ignored) {
        }
        return isHeader(parent.getChildViewHolder(view).getItemViewType());
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, needDivider(parent, view) ? mDividerHeight : 0);
        } else {
            outRect.set(0, 0, needDivider(parent, view) ? mDividerHeight : 0, 0);
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
                if (needDivider(parent, child)) {
                    final int bottom = top + mDividerHeight;
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
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
                if (needDivider(parent, child)) {
                    final int right = left + mDividerHeight;
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
            }
        }
    }

    @Override
    public void tint() {
        mDivider = ThemeUtils.tintDrawable(mDivider, ThemeUtils.getColorByAttr(mContext, R.attr.colorDivider));
    }
}
