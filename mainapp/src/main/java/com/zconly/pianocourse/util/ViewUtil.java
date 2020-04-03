package com.zconly.pianocourse.util;

import android.text.TextPaint;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.adapter.viewholder.CommonBannerHolder;
import com.zconly.pianocourse.base.MainApplication;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.BaseBean;

import java.util.List;

public class ViewUtil {

    public static final float BANNER_TYPE_0 = 360f / 184f;

    public static void setTextPaint(TextPaint ds) {
        ds.setColor(MainApplication.getInstance().getResources().getColor(R.color.color_red));
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }

    // ConvenientBanner
    public static <T extends BaseBean> void updateBanner(ConvenientBanner<T> bannerView, List<T> bannerData) {
        updateBanner(bannerView, bannerData, new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new CommonBannerHolder<T>(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_pager_image_large;
            }

        });
    }

    public static <T extends BaseBean> void updateBanner(ConvenientBanner<T> bannerView, List<T> bannerData,
                                                         CBViewHolderCreator creator) {
        if (bannerView == null)
            return;
        if (ArrayUtil.isEmpty(bannerData)) {
            bannerView.setVisibility(View.GONE);
            return;
        }
        bannerView.setVisibility(View.VISIBLE);
        bannerView.setPages(creator, bannerData)
                .setPageIndicator(new int[]{R.drawable.shape_circle_white_30_4, R.drawable.shape_circle_white_4})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .startTurning(3000)
                .setOnItemClickListener(position -> {

                    T data = bannerData.get(position);

                    if (data instanceof BannerBean) {
                        // TODO: 2020/4/2
                    }
                });
    }

    public static void setSelection(EditText... ets) {
        if (ets == null)
            return;
        for (EditText et : ets)
            et.setSelection(et.getText().length());
    }

    public static int getBannerHeight(int width, float type) {
        return (int) (width / BANNER_TYPE_0);
    }

    // 取消RecyclerView Animation
    public static void cancelRecyclerViewAnim(RecyclerView recyclerView) {
        DefaultItemAnimator animator = ((DefaultItemAnimator) recyclerView.getItemAnimator());
        if (animator != null)
            animator.setSupportsChangeAnimations(false);
    }

    // 解决item错乱的问题
    public static void parseStaggeredGrid(RecyclerView recyclerView) {
        if (!(recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager))
            return;
        StaggeredGridLayoutManager lm = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
        lm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    recyclerView.invalidateItemDecorations();
                }
            }
        });
    }
}
