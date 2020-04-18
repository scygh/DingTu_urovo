package com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.lianxi.dingtu.dingtu_urovo.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_urovo.app.api.EventBusTags;
import com.lianxi.dingtu.dingtu_urovo.app.entity.CardTypeTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.RegisterParam;
import com.lianxi.dingtu.dingtu_urovo.app.entity.SubsidyTo;
import com.lianxi.dingtu.dingtu_urovo.di.component.DaggerStepTwoComponent;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.StepTwoContract;
import com.lianxi.dingtu.dingtu_urovo.mvp.presenter.StepTwoPresenter;

import com.lianxi.dingtu.dingtu_urovo.R;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.widget.LoadDialog;
import com.shuhart.stepview.StepView;


import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 09:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class StepTwoActivity extends BaseActivity<StepTwoPresenter> implements StepTwoContract.View {
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.step_view)
    StepView stepView;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.level)
    TextView level;
    @BindView(R.id.ll_chong)
    LinearLayout llChong;
    @BindView(R.id.ll_zeng)
    LinearLayout llZeng;
    @BindView(R.id.cost)
    TextView cost;

    private OptionsPickerView pvCustomOptionsLeve, pvCustomOptionsType;
    @BindView(R.id.et_donate)
    EditText et_donate;
    @BindView(R.id.et_amount)
    EditText et_amount;
    private static final int MESSAGE_SEARCH = 0x1;
    private static long INTERVAL = 1000; // 输入变化时间间隔
    int card_type;
    int card_state;
    int subsidy_level;
    int days;
    double subsidy;
    RegisterParam param;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MESSAGE_SEARCH) {
                if (!TextUtils.isEmpty(et_amount.getText().toString().trim()))
                    mPresenter.onDonate(card_type, Double.valueOf(et_amount.getText().toString().trim()));
                else
                    et_donate.setText("");
            }
        }
    };

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStepTwoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_step_two; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("开户");
        setupWindowAnimations();
        param = (RegisterParam) getIntent().getSerializableExtra(AppConstant.ActivityIntent.STEP1);
        mPresenter.onCardType();
        mPresenter.onSubsidyLeve();
        List<String> steps = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            steps.add("第 " + (i + 1) + " 步");
        }
//        steps.set(steps.size() - 1, steps.get(steps.size() - 1) + "  放置卡片，完成写卡");
        stepView.setSteps(steps);
        if (0 < stepView.getStepCount() - 1) {
            stepView.go(1, true);
        } else {
            stepView.done(true);
        }

        et_amount.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (card_type == 0)
                    showMessage("请先选择卡片类型和补贴级别");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mHandler.hasMessages(MESSAGE_SEARCH)) {
                    mHandler.removeMessages(MESSAGE_SEARCH);
                }
                mHandler.sendEmptyMessageDelayed(MESSAGE_SEARCH, INTERVAL);
            }
        });
        mPresenter.onDonateSwitch();
    }

    private void setupWindowAnimations() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_right_to_center);   //得到一个LayoutAnimationController对象；
        LayoutAnimationController controller = new LayoutAnimationController(animation);   //设置控件显示的顺序；
        controller.setOrder(LayoutAnimationController.ORDER_REVERSE);   //设置控件显示间隔时间；
        controller.setDelay(0.3f);

        root.setLayoutAnimation(controller);
        root.startLayoutAnimation();
    }

    @Override
    public void showLoading() {
        LoadDialog loadDialog = LoadDialog.getInstance();
        loadDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.load_dialog);
        if (!LoadDialog.getInstance().isAdded() && null == getSupportFragmentManager().findFragmentByTag(TAG)) {
            LoadDialog.getInstance().show(getSupportFragmentManager(), TAG);
        }
    }

    @Override
    public void hideLoading() {
        LoadDialog.getInstance().dismiss();
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

    @OnClick({R.id.next, R.id.ll_type, R.id.ll_level})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.next:
                param.setCardType(card_type);
                param.setCardState(card_state);
//                param.setCardTypeName(type.getText().toString());
                param.setSubsidyLevel(subsidy_level);
//                param.setSubsidyLevelName(level.getText().toString());
                param.setSubsidy(subsidy);
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_WEEK, days);
                param.setDeadlineTT(c.getTime());
                param.setCost(Double.valueOf(cost.getText().toString()));
                param.setCash(Double.valueOf(TextUtils.isEmpty(et_amount.getText().toString().trim()) ? "0" : et_amount.getText().toString().trim()));
                param.setDonate(Double.valueOf(TextUtils.isEmpty(et_donate.getText().toString().trim()) ? "0" : et_donate.getText().toString().trim()));
                Intent intent = new Intent();
                intent.putExtra(AppConstant.ActivityIntent.STEP2, param);
                intent.setClass(StepTwoActivity.this, StepThreeActivity.class);
                launchActivity(intent);
                break;
            case R.id.ll_type:
                if (pvCustomOptionsType != null)
                    pvCustomOptionsType.show();
                break;
            case R.id.ll_level:
                if (pvCustomOptionsLeve != null)
                    pvCustomOptionsLeve.show();
                break;
        }
    }


    @Override
    public void onChoiceType(List<CardTypeTo> list) {
        type.setText(list.get(0).getName());
        card_type = list.get(0).getId();
        days = list.get(0).getExpiryDate();
        card_state = list.get(0).getState();
        cost.setText(String.format("%.2f", list.get(0).getDefaultCost()));
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptionsType = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = list.get(options1).getPickerViewText();
                card_type = list.get(options1).getId();
                days = list.get(options1).getExpiryDate();
                card_state = list.get(options1).getState();
                cost.setText(String.format("%.2f", list.get(options1).getDefaultCost()));
                type.setText(tx);
                if (tx.contains("计次")) {
                    llChong.setVisibility(View.INVISIBLE);
                    llZeng.setVisibility(View.INVISIBLE);
                } else {
                    llChong.setVisibility(View.VISIBLE);
                    llZeng.setVisibility(View.VISIBLE);
                    mPresenter.onDonateSwitch();
                }

            }
        })
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = (TextView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptionsType.returnData();
                                pvCustomOptionsType.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptionsType.dismiss();
                            }
                        });


                    }
                })
                .isDialog(true)
                .build();

        pvCustomOptionsType.setPicker(list);//添加数据
    }

    @Override
    public void onChoiceLeve(List<SubsidyTo> list) {
        level.setText(list.get(0).getName());
        subsidy_level = list.get(0).getLeve();
        subsidy = list.get(0).getAmount();
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptionsLeve = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = list.get(options1).getPickerViewText();
                level.setText(tx);
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = (TextView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptionsLeve.returnData();
                                pvCustomOptionsLeve.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptionsLeve.dismiss();
                            }
                        });


                    }
                })
                .isDialog(true)
                .build();

        pvCustomOptionsLeve.setPicker(list);//添加数据
    }

    @Override
    public void onDonate(double d) {
        et_donate.setText(String.format("%.2f", d));
    }

    @Override
    public void onDonateSwitch(String str) {
        if (TextUtils.equals("0", str)) {
            et_donate.setText("");
            llZeng.setVisibility(View.INVISIBLE);
        } else if (TextUtils.equals("1", str)) {
            llZeng.setVisibility(View.VISIBLE);
        }

    }

    @Subscriber(tag = EventBusTags.STEP_DONE)
    public void closePage(String s) {
        finish();
    }
}
