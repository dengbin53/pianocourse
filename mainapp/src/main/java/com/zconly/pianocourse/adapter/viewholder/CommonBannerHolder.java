package com.zconly.pianocourse.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.util.DataUtil;
import com.zconly.pianocourse.util.ImgLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * banner holder
 */
public class CommonBannerHolder<T> extends Holder<T> {

    @BindView(R.id.banner_iv)
    ImageView imageView;
    @BindView(R.id.banner_tv)
    TextView tv;

    public CommonBannerHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void initView(View itemView) {
        
    }

    @Override
    public void updateUI(T data) {
        if (data instanceof BannerBean) {
            String url = DataUtil.getImgUrl(((BannerBean) data).getCover());
            ImgLoader.showImg(url, imageView, R.drawable.shape_corner_gray_light);
            tv.setText(((BannerBean) data).getTitle());
        }
    }

}
