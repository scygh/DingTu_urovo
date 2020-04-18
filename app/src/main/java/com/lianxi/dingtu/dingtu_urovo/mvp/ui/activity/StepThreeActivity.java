package com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.lianxi.dingtu.dingtu_urovo.app.Utils.StringUtils;
import com.lianxi.dingtu.dingtu_urovo.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_urovo.app.api.EventBusTags;
import com.lianxi.dingtu.dingtu_urovo.app.entity.DepartmentTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.RegisterParam;
import com.lianxi.dingtu.dingtu_urovo.di.component.DaggerStepThreeComponent;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.StepThreeContract;
import com.lianxi.dingtu.dingtu_urovo.mvp.presenter.StepThreePresenter;

import com.lianxi.dingtu.dingtu_urovo.R;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.widget.LoadDialog;
import com.shuhart.stepview.StepView;


import org.simple.eventbus.Subscriber;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 09:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class StepThreeActivity extends BaseActivity<StepThreePresenter> implements StepThreeContract.View {
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.step_view)
    StepView stepView;
    @BindView(R.id.number)
    EditText number;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.ll_mima)
    LinearLayout llMima;
    @BindView(R.id.deadline)
    TextView deadline;
    private OptionsPickerView pvCustomOptions;
    @BindView(R.id.department)
    TextView department;
    RegisterParam param;
    private TimePickerView pvTime;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStepThreeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_step_three; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("开户");
        setupWindowAnimations();
        param = (RegisterParam) getIntent().getSerializableExtra(AppConstant.ActivityIntent.STEP2);
        mPresenter.onDepartment();
        mPresenter.getNextNumber();
        List<String> steps = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            steps.add("第 " + (i + 1) + " 步");
        }
//        steps.set(steps.size() - 1, steps.get(steps.size() - 1) + "  放置卡片，完成写卡");
        stepView.setSteps(steps);
        if (1 < stepView.getStepCount() - 1) {
            stepView.go(2, true);
        } else {
            stepView.done(true);
        }
        initTimePicker();
    }

    private void setupWindowAnimations() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_right_to_center);   //得到一个LayoutAnimationController对象；
        LayoutAnimationController controller = new LayoutAnimationController(animation);   //设置控件显示的顺序；
        controller.setOrder(LayoutAnimationController.ORDER_REVERSE);   //设置控件显示间隔时间；
        controller.setDelay(0.3f);

        root.setLayoutAnimation(controller);
        root.startLayoutAnimation();
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出
        Calendar calendar = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        Calendar endDate = Calendar.getInstance();
        endDate.set(3000, 1, 1);
        Calendar _calendar = Calendar.getInstance();
        _calendar.setTime(param.getDeadlineTT());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        deadline.setText(df.format(param.getDeadlineTT()));
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                deadline.setText(df.format(date));
                param.setDeadlineTT(date);
                Log.i("pvTime", "onTimeSelect");
            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setTitleText("到期时间")
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setRangDate(startDate, endDate)
                .setDate(_calendar)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
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

    @Override
    public void onDepartment(List<DepartmentTo> list) {
        department.setText(list.get(0).getName());
        param.setDepartmentID(list.get(0).getId());
        param.setDepartmentName(list.get(0).getName());
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = list.get(options1).getPickerViewText();
                department.setText(tx);
                param.setDepartmentID(list.get(options1).getId());
                param.setDepartmentName(list.get(options1).getName());
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
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });


                    }
                })
                .isDialog(true)
                .build();

        pvCustomOptions.setPicker(list);//添加数据
    }

    @Override
    public void onRegister() {
        Intent intent = new Intent();
        intent.putExtra(AppConstant.ActivityIntent.STEP3, param);
        intent.setClass(StepThreeActivity.this, ClaimActivity.class);
        startActivity(intent);

    }

    @Override
    public void onNextNumber(int number) {
        param.setNumber(number);
    }


    @OnClick({R.id.ll_time, R.id.ll_dep, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_time:
                pvTime.show(view);
                break;
            case R.id.ll_dep:
                if (pvCustomOptions != null)
                    pvCustomOptions.show();
                break;
            case R.id.submit:
                if (TextUtils.isEmpty(number.getText().toString().trim())) {
                    Toasty.error(StepThreeActivity.this, "卡号是必填的", Toast.LENGTH_SHORT, true).show();
                    return;
                }

                param.setDeadline(deadline.getText().toString() + " 00:00:00");
                Date date = new Date();
//                param.setCardCreateTime(StringUtils.ConverToString(date));
                param.setUserCreateTime(StringUtils.ConverToString(date));
                param.setSerialNo(number.getText().toString().trim());
                param.setPayKey(password.getText().toString().trim());
                Log.e(TAG, "onViewClicked: " + JSON.toJSONString(param));
                mPresenter.onRegister(param);
                break;
        }
    }

    @Subscriber(tag = EventBusTags.STEP_DONE)
    public void closePage(String s) {
        finish();
    }
}
