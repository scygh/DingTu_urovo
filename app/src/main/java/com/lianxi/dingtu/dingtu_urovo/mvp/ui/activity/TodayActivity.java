package com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.dingtu_urovo.R;
import com.lianxi.dingtu.dingtu_urovo.app.entity.MachineAmountTo;
import com.lianxi.dingtu.dingtu_urovo.app.event.ReplyEvent;
import com.lianxi.dingtu.dingtu_urovo.di.component.DaggerTodayComponent;
import com.lianxi.dingtu.dingtu_urovo.di.module.TodayModule;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.TodayContract;
import com.lianxi.dingtu.dingtu_urovo.mvp.presenter.TodayPresenter;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 15:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class TodayActivity extends BaseActivity<TodayPresenter> implements TodayContract.View {
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.loading)
    LinearLayout loading;
    @BindView(R.id.today)
    TextView today;
    @BindView(R.id.yesterday)
    TextView yesterday;
    @BindView(R.id.this_month)
    TextView thisMonth;
    @BindView(R.id.last_month)
    TextView lastMonth;
    @BindView(R.id.time_today)
    TextView time_today;
    @BindView(R.id.time_yesterday)
    TextView time_yesterday;
    @BindView(R.id.time_this_month)
    TextView time_thisMonth;
    @BindView(R.id.time_last_month)
    TextView time_lastMonth;
    @BindView(R.id.pay_count)
    TextView pay_count;
    @BindView(R.id.time_pay_count)
    TextView time_pay_count;
    int flag = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTodayComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_today; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("当前收益");
        mPresenter.getMachineAmount();
        mPresenter.getMachineTimeCount();
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

    @Subscriber
    private void onReplyEvent(ReplyEvent event) {
        flag += event.hasDone;
        if(flag == 2){
            loading.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

        }
    }

    @Override
    public void MachineAmount(MachineAmountTo machineAmountTo) {
        Message msg = handler.obtainMessage(1);
        msg.obj = machineAmountTo;
        handler.sendMessage(msg);
        Log.e(TAG, "MachineAmount: 成功" );

    }

    @Override
    public void MachineTimeCount(MachineAmountTo machineAmountTo) {
        Message msg = handler.obtainMessage(2);
        msg.obj = machineAmountTo;
        handler.sendMessage(msg);
        Log.e(TAG, "MachineAmount: 成功" );
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    MachineAmountTo machineAmountTo = (MachineAmountTo) msg.obj;
                    today.setText(String.format("%.2f", machineAmountTo.getToday()));
                    thisMonth.setText(String.format("当月消费：%.2f", machineAmountTo.getCurrentMonth()));
                    yesterday.setText(String.format("%.2f", machineAmountTo.getLastDay()));
                    lastMonth.setText(String.format("上月消费：%.2f", machineAmountTo.getLastMonth()));
                    pay_count.setText("今日刷卡次数："+ machineAmountTo.getToDayCount());
                    break;
                case 2:
                    MachineAmountTo machineAmountTo2 = (MachineAmountTo) msg.obj;
                    time_today.setText(String.format("%.2f", machineAmountTo2.getToday()));
                    time_thisMonth.setText(String.format("当月消费：%.2f", machineAmountTo2.getCurrentMonth()));
                    time_yesterday.setText(String.format("%.2f", machineAmountTo2.getLastDay()));
                    time_lastMonth.setText(String.format("上月消费：%.2f", machineAmountTo2.getLastMonth()));
                    time_pay_count.setText("今天刷卡次数："+ machineAmountTo2.getToDayCount());
                    break;
            }
        }
    };
}
