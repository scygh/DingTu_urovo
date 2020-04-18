package com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.lianxi.dingtu.dingtu_urovo.app.Utils.AudioUtils;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.DoubleUtil;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.UserInfoHelper;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.card.BytesUtils;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.card.BytesUtils2;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.card.ReadCardUtils;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.card.SoundTool;
import com.lianxi.dingtu.dingtu_urovo.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_urovo.app.api.EventBusTags;
import com.lianxi.dingtu.dingtu_urovo.app.entity.CardInfoBean;
import com.lianxi.dingtu.dingtu_urovo.app.entity.CardInfoTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.RegisterParam;
import com.lianxi.dingtu.dingtu_urovo.app.listening.RFCardListening;
import com.lianxi.dingtu.dingtu_urovo.di.component.DaggerClaimComponent;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.ClaimContract;
import com.lianxi.dingtu.dingtu_urovo.mvp.presenter.ClaimPresenter;

import com.lianxi.dingtu.dingtu_urovo.R;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.widget.LoadDialog;
import com.shuhart.stepview.StepView;


import org.simple.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 09:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ClaimActivity extends BaseActivity<ClaimPresenter> implements ClaimContract.View {
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.step_view)
    StepView stepView;
    RegisterParam param;
    CardInfoBean mCardInfoBean;
    Tag tag;
    @BindView(R.id.cardview1)
    CardView cardview1;
    @BindView(R.id.cardview2) CardView cardview2;
    @BindView(R.id.cardview3) CardView cardview3;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.type) TextView type;
    @BindView(R.id.number) TextView number;
    @BindView(R.id.balance) TextView balance;
    @BindView(R.id.tv1) TextView tv1;
    @BindView(R.id.donate) TextView donate;
    @BindView(R.id.subsidies) TextView subsidies;

    boolean isFirst = true;
    boolean isPayBegin = true;

    private ReadCardUtils readCardUtils;
    private String key;
    private String data;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerClaimComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_claim; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("开户领卡");
        setupWindowAnimations();
        readCardUtils = new ReadCardUtils();
        readCardUtils.init();
        mCardInfoBean= new CardInfoBean();
        key = (String) SpUtils.get(this, AppConstant.NFC.NFC_KEY, "");

        param = (RegisterParam) getIntent().getSerializableExtra(AppConstant.ActivityIntent.STEP3);

        List<String> steps = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            steps.add("第 " + (i + 1) + " 步");
        }
//        steps.set(steps.size() - 1, steps.get(steps.size() - 1) + "  放置卡片，完成写卡");
        stepView.setSteps(steps);
        if (1 < stepView.getStepCount() - 1) {
            stepView.go(3, true);
        } else {
            stepView.done(true);
        }
        readCardUtils = new ReadCardUtils();
        readCardUtils.init();
        readCardUtils.run();

        readCardUtils.setRFCardListening(new RFCardListening() {
            @Override
            public void findRFCardListening(String uuid) {
                if (readCardUtils.Authen(4, key)) {
                    readCardUtils.Read(4);
                }
            }

            @Override
            public void readRFCardListening(String data) {
                Log.e(TAG, "handleMessage: data:"+data);
                String num = data.substring(0, 6);
                mCardInfoBean.setNum(Integer.parseInt(num, 16));
                mCardInfoBean.setName(BytesUtils2.getString(BytesUtils.hexStringToBytes(data.substring(8, 26))));
                String code = data.substring(26, 30);
                mCardInfoBean.setCode(code);
                String level_type = data.substring(30);
                mCardInfoBean.setType(Integer.parseInt(level_type.substring(1), 16));
                mCardInfoBean.setLevel(Integer.parseInt(level_type.substring(0, 1), 16));
                if (!TextUtils.equals("0000", mCardInfoBean.getCode())) {
                    AudioUtils.getInstance().speakText("非空卡片，操作失败");
                    showMessage("非空卡片，操作失败");
                    return;
                }
                if (!isPayBegin){
                    return;
                }
                if (!isFirst) {
                    AudioUtils.getInstance().speakText("请勿重复领卡");
                    showMessage("请勿重复领卡");
                    return;
                }
                SoundTool.getMySound(ClaimActivity.this).playMusic("success");
                mPresenter.onByNumber(param.getNumber());
            }

            @Override
            public void noFindRFCardListening() {
                isPayBegin=true;
            }

            @Override
            public void failReadRFCardListening() {
                isPayBegin=true;
            }
        });
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

    @Override public void onCardInfo(CardInfoTo cardInfoTo) {
        isPayBegin=false;
        name.setText(cardInfoTo.getName());
        number.setText("NO." + cardInfoTo.getSerialNo());
        String amount = String.format("￥%.2f", cardInfoTo.getCash());
        SpannableStringBuilder builder = new SpannableStringBuilder(amount);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 24)), amount.indexOf("￥"), amount.indexOf("￥") + 1
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 24)), amount.indexOf("."), amount.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        balance.setText(builder);
        donate.setText(String.format("%.2f", cardInfoTo.getDonate()));
        subsidies.setText(String.format("%.2f", cardInfoTo.getSubsidy()));

        type.setText(cardInfoTo.getCardTypeName());
        time.setText("开户时间 " + cardInfoTo.getCardCreateTime());

        mCardInfoBean.setNum(cardInfoTo.getNumber());
        mCardInfoBean.setName(cardInfoTo.getName());
        String _cod = Integer.toHexString(UserInfoHelper.getInstance(this).getCode());
        String str = addZeroForNum(_cod, 4);
        mCardInfoBean.setCode(str);

        mCardInfoBean.setCash_account(DoubleUtil.add2(cardInfoTo.getCash(), cardInfoTo.getDonate()));
        mCardInfoBean.setAllowance_account(0);
        mCardInfoBean.setConsumption_num(0);

        mCardInfoBean.setType(cardInfoTo.getCardType());
        mCardInfoBean.setLevel(cardInfoTo.getSubsidyLevel());
        mCardInfoBean.setGuaranteed_amount(cardInfoTo.getForegift());
        mCardInfoBean.setCard_validity(cardInfoTo.getDeadline().substring(0, 10));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String _str = formatter.format(curDate);
        mCardInfoBean.setSpending_time(_str);
        Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
        ca.setTime(new Date()); // 设置时间为当前时间
        ca.add(Calendar.MONTH, -1);// 月份减1
        Date resultDate = ca.getTime(); // 结果
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        System.out.println(sdf.format(resultDate));
        mCardInfoBean.setSubsidies_time(sdf.format(resultDate));
        Log.e(TAG, "comfirm: " + JSON.toJSONString(mCardInfoBean));
        boolean success =readCardUtils.Write(mCardInfoBean);
        Log.e(TAG, "onCardInfo: "+success );

        if (success) {
            mPresenter.onObtainByNumber(param.getNumber());
            readCardUtils.close();

        }

    }


    @Override public void onSuccess(boolean yes) {
        isFirst = false;
        cardview1.setVisibility(View.GONE);
        cardview2.setVisibility(View.VISIBLE);
        cardview3.setVisibility(View.VISIBLE);
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom);
        cardview2.setLayoutAnimation(controller);
        cardview2.scheduleLayoutAnimation();
        cardview3.setLayoutAnimation(controller);
        cardview3.scheduleLayoutAnimation();
        EventBus.getDefault().post("step_done", EventBusTags.STEP_DONE);
    }

    public static String addZeroForNum(String str, int strLength) {

        if (TextUtils.isEmpty(str)) {
            return "";
        }

        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            sb.append("0").append(str);// 左补0
//            sb.append(str).append("0");//右补0
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    @Override
    protected void onDestroy() {
        readCardUtils.close();
        super.onDestroy();
    }
}
