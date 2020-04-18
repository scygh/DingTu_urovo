package com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.lianxi.dingtu.dingtu_urovo.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_urovo.app.entity.DepositReportTo;
import com.lianxi.dingtu.dingtu_urovo.di.component.DaggerChooseComponent;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.ChooseContract;
import com.lianxi.dingtu.dingtu_urovo.mvp.presenter.ChoosePresenter;

import com.lianxi.dingtu.dingtu_urovo.R;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/21/2019 15:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ChooseActivity extends BaseActivity<ChoosePresenter> implements ChooseContract.View {
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.after_balance) TextView afterBalance;
    @BindView(R.id.detail_type) TextView detailType;
    @BindView(R.id.finance) TextView finance;
    @BindView(R.id.trade_date_time) TextView tradeDateTime;
    @BindView(R.id.card_type_name) TextView cardTypeName;
    @BindView(R.id.amount) TextView amount;
    @BindView(R.id.money) TextView money;
    @BindView(R.id.donate) TextView donate;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChooseComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_choose; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("充值详情");
        DepositReportTo reportTo = (DepositReportTo) getIntent().getSerializableExtra(AppConstant.ActivityIntent.MODEL_DEPOSIT);
        if (reportTo != null) {
            name.setText(reportTo.getName());
            cardTypeName.setText(reportTo.getCardTypeName());
            tradeDateTime.setText("交易时间 " + reportTo.getTradeDateTime());
            afterBalance.setText(String.format("￥%.2f", reportTo.getAfterBalance()));
            amount.setText(String.format("%.2f", reportTo.getAmount()));
            money.setText(String.format("%.2f", reportTo.getMoney()));
            donate.setText(String.format("%.2f", reportTo.getDonate()));
            String value ;
            switch (reportTo.getFinance()) {
                case 0:
                    value = "现金";
                    break;
                case 1:
                    value = "补贴";
                    break;
                case 2:
                    value = "计次";
                    break;
                case 3:
                    value = "赠送";
                    break;
                case 4:
                    value = "积分";
                    break;
                default:
                    value = "";
                    break;
            }
            finance.setText("账户类型："+value);
            String value2 ;
            switch (reportTo.getDetailType()) {
                case 1:
                    value2 = "充值";
                    break;
                case 2:
                    value2 = "退款";
                    break;
                case 3:
                    value2 = "纠错";
                    break;
                case 4:
                    value2 = "开户";
                    break;
                case 5:
                    value2 = "补卡";
                    break;
                case 6:
                    value2 = "注销";
                    break;
                default:
                    value2 = "";
                    break;
            }
            detailType.setText("记录类型："+value2);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
