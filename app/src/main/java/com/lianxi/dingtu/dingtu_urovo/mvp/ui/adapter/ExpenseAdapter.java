package com.lianxi.dingtu.dingtu_urovo.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lianxi.dingtu.dingtu_urovo.R;
import com.lianxi.dingtu.dingtu_urovo.app.entity.ExpenseReportTo;

import java.util.List;

public class ExpenseAdapter extends BaseQuickAdapter<ExpenseReportTo, BaseViewHolder> {
    public ExpenseAdapter(int layoutResId, @Nullable List<ExpenseReportTo> data) {
        super(layoutResId, data);
    }

    @Override protected void convert(BaseViewHolder helper, ExpenseReportTo item) {
//        AppComponent mAppComponent = ((App) helper.itemView.getContext().getApplicationContext()).getAppComponent();
//        Typeface iconfont = ITypeface.getTypeface(mAppComponent.application());
//        ((TextView) helper.itemView.findViewById(R.id.wode)).setTypeface(iconfont);
        helper.setText(R.id.name, item.getName())
                .setText(R.id.time, item.getTradeDateTime())
                .setText(R.id.money, String.format("- %.2f", item.getAmount()));
    }
}
