package com.zconly.pianocourse.adapter.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * @Description: grid 分割线
 * @Author: dengbin
 * @CreateDate: 2020/3/9 15:49
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/9 15:49
 * @UpdateRemark: 更新说明
 */
public class GridDecoration extends RecyclerView.ItemDecoration {

    private int dividerHeight;
    private int dividerHeightHalf;

    public GridDecoration(int dividerHeight) {
        this.dividerHeight = dividerHeight;
        dividerHeightHalf = (int) (dividerHeight / 2f);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent,
                               @NonNull RecyclerView.State state) {

        int spanIndex;
        int spanCount;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            spanIndex = parent.getChildAdapterPosition(view);
            RecyclerView.Adapter adapter = parent.getAdapter();
            if (adapter instanceof BaseQuickAdapter) {
                spanIndex = spanIndex - ((BaseQuickAdapter) adapter).getHeaderLayoutCount();
            }

            outRect.set(spanIndex % spanCount == 0 ? dividerHeight : dividerHeightHalf, dividerHeight,
                    spanIndex % spanCount == spanCount - 1 ? dividerHeight : dividerHeightHalf, 0);

        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager.LayoutParams params
                    = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();

            spanIndex = params.getSpanIndex();

            RecyclerView.Adapter adapter = parent.getAdapter();
            if (adapter instanceof BaseQuickAdapter) {
                int headerCount = ((BaseQuickAdapter) adapter).getHeaderLayoutCount();
                int position = parent.getChildAdapterPosition(view);
                if (headerCount - position > 0) {
                    outRect.set(0, 0, 0, 0);
                    return;
                }
            }

            int t = spanIndex % 2;
            outRect.set(t == 0 ? dividerHeight : dividerHeightHalf, dividerHeight,
                    t == 0 ? dividerHeightHalf : dividerHeight, 0);
        }

    }

}
