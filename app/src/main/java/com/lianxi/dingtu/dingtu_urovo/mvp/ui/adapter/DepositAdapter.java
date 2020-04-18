package com.lianxi.dingtu.dingtu_urovo.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lianxi.dingtu.dingtu_urovo.R;
import com.lianxi.dingtu.dingtu_urovo.app.entity.DepositReportTo;


import java.util.List;

public class DepositAdapter extends BaseQuickAdapter<DepositReportTo, BaseViewHolder> {

    public DepositAdapter(int layoutResId, @Nullable List<DepositReportTo> data) {
        super(layoutResId, data);
    }

    @Override protected void convert(BaseViewHolder helper, DepositReportTo item) {
        helper.setText(R.id.name, item.getName())
                .setText(R.id.time, item.getTradeDateTime())
                .setText(R.id.money, String.format("+ %.2f", item.getAmount()));
    }

}
