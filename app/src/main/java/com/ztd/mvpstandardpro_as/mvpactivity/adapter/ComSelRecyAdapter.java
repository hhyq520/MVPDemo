package com.ztd.mvpstandardpro_as.mvpactivity.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ztd.mvpstandardpro_as.R;
import com.ztd.mvpstandardpro_as.bean.Siteinfo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/9.
 */

public class ComSelRecyAdapter extends BaseQuickAdapter<Siteinfo, BaseViewHolder> {
    private Context context;
    public ComSelRecyAdapter(List<Siteinfo> data, Context context) {
        super(R.layout.item_sel_com, data);
        this.context=context;
    }
    @Override
    protected void convert(final BaseViewHolder helper, Siteinfo item) {
        helper.setText(R.id.tv_no,item.getSiteCode());
        helper.setText(R.id.tv_name,item.getSiteName());
    }
}

