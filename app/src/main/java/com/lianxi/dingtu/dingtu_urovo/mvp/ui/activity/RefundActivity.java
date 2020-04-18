package com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.dingtu_urovo.R;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.AntiShake;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.AudioUtils;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.ShakeAnimation;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.UserInfoHelper;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.card.ReadCardUtils;
import com.lianxi.dingtu.dingtu_urovo.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_urovo.app.entity.CardInfoBean;
import com.lianxi.dingtu.dingtu_urovo.app.entity.CardInfoTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.MoneyParam;
import com.lianxi.dingtu.dingtu_urovo.app.listening.RFCardListening;
import com.lianxi.dingtu.dingtu_urovo.di.component.DaggerRefundComponent;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.RefundContract;
import com.lianxi.dingtu.dingtu_urovo.mvp.presenter.RefundPresenter;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.widget.LoadDialog;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.widget.label.LabelRelativeLayout;


import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 15:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RefundActivity extends BaseActivity<RefundPresenter> implements RefundContract.View {
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.number) TextView number;
    @BindView(R.id.balance) TextView balance;
    @BindView(R.id.donate) TextView donate;
    @BindView(R.id.subsidies) TextView subsidies;
    @BindView(R.id.cardview)
    CardView cardview;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.label_rl)
    LabelRelativeLayout labelRelativeLayout;
    LoadDialog loadDialog;
    CardInfoBean mCardInfoBean;
    @BindView(R.id.et_amount)
    EditText etAmount;
    @BindView(R.id.et_donate) EditText etDonate;
    @BindView(R.id.et_money) EditText etMoney;
    CardInfoTo mCardInfoTo;
    private boolean isPayBegin = true;
    private ReadCardUtils readCardUtils;
    private String key;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRefundComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_refund; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("退款");
        setupWindowAnimations();

        loadDialog = LoadDialog.getInstance();
        loadDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.load_dialog);

        etAmount.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                etMoney.setText(s.toString());

            }
        });

        submit.setEnabled(false);
        submit.setText("请先刷卡，启用按钮");

        key = (String) SpUtils.get(this, AppConstant.NFC.NFC_KEY, "");

        readCardUtils = new ReadCardUtils();
        readCardUtils.init();
        readCardUtils.run();
        readCardUtils.setRFCardListening(new RFCardListening() {
            @Override
            public void findRFCardListening(String s) {
                if (readCardUtils.Authen(4, key)) {
                    readCardUtils.Read(4);
                }
            }

            @Override
            public void readRFCardListening(String data) {
                UserInfoHelper mUserInfoHelper = UserInfoHelper.getInstance(RefundActivity.this);
                mCardInfoBean = readCardUtils.cardInfoBean(data);
                if (TextUtils.isEmpty(mCardInfoBean.getCode())) {
                    return;
                } else {
                    if (TextUtils.equals("0000", mCardInfoBean.getCode())) {
                        AudioUtils.getInstance().speakText("空白卡");
                        showMessage("空白卡");
                        return;
                    } else if (Integer.parseInt(mCardInfoBean.getCode(), 16) != mUserInfoHelper.getCode()) {
                        AudioUtils.getInstance().speakText("非本单位卡");
                        showMessage("非本单位卡");
                        return;
                    }
                }
                if (!isPayBegin){
                    return;
                }
                mPresenter.getCardInfo(mCardInfoBean.getNum());
                submit.setEnabled(true);
                submit.setText("确认退款");

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

    private void setupWindowAnimations(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_left_to_center);   //得到一个LayoutAnimationController对象；
        LayoutAnimationController controller = new LayoutAnimationController(animation);   //设置控件显示的顺序；
        controller.setOrder(LayoutAnimationController.ORDER_REVERSE);   //设置控件显示间隔时间；
        controller.setDelay(0.3f);

        root.setLayoutAnimation(controller);
        root.startLayoutAnimation();
    }

    @Override
    public void showLoading() {
        if (!loadDialog.isAdded() && null == getSupportFragmentManager().findFragmentByTag(TAG))
            loadDialog.show(getSupportFragmentManager(), TAG);
    }

    @Override
    public void hideLoading() {
        loadDialog.dismiss();
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
        mCardInfoTo = cardInfoTo;
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

        String value ;
        switch (cardInfoTo.getUserState()) {
            case 0:
                value = "未开户";
                break;
            case 1:
                value = "正常";
                break;
            case 2:
                value = "已挂失";
                break;
            case 3:
                value = "已注销";
                break;
            case 4:
                value = "未审核";
                break;
            case 5:
                value = "审核失败";
                break;
            default:
                value = "";
                break;
        }
        labelRelativeLayout.setTextContent(value);
        ObjectAnimator nopeAnimator = ShakeAnimation.nope(cardview);
        nopeAnimator.setRepeatCount(ValueAnimator.INFINITE);
        nopeAnimator.start();
        Flowable.just(1)
                .delay(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        nopeAnimator.end();
                    }
                });
    }
    protected AntiShake util = new AntiShake();

    @OnClick(R.id.submit) public void onViewClicked() {
        if (util.check()) return;
        if (Double.parseDouble(etAmount.getText().toString())>=mCardInfoTo.getCash()){
            Toasty.warning(RefundActivity.this, "退款金额不能大于余额", Toast.LENGTH_SHORT, true).show();
            return;
        }
        submit.setEnabled(false);
        submit.setText("重新刷卡，启用按钮");
        String deviceID = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
        MoneyParam param = new MoneyParam();

        param.setUserID(mCardInfoTo.getUserID());
        param.setNumber(mCardInfoTo.getNumber());
        param.setDeviceID(Integer.valueOf(TextUtils.isEmpty(deviceID) ? "1" : deviceID));
        param.setAmount(TextUtils.isEmpty(etAmount.getText().toString().trim()) ? 0 : Double.valueOf(etAmount.getText().toString().trim()));
        param.setMoney(TextUtils.isEmpty(etMoney.getText().toString().trim()) ? 0 : Double.valueOf(etMoney.getText().toString().trim()));
        param.setDonate(TextUtils.isEmpty(etDonate.getText().toString().trim()) ? 0 : Double.valueOf(etDonate.getText().toString().trim()));
        mPresenter.onRefund(param);

    }

    @Override
    protected void onDestroy() {
        readCardUtils.close();
        super.onDestroy();
    }
}
