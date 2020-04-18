package com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity;

import android.content.Intent;
import android.device.PrinterManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.dingtu_urovo.R;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.AdConstant;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.AnimSpring;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.AntiShake;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.AudioUtils;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.DoubleUtil;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.UserInfoHelper;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.card.BytesUtils;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.card.BytesUtils2;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.card.PrinterUtils;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.card.ReadCardUtils;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.card.SoundTool;
import com.lianxi.dingtu.dingtu_urovo.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_urovo.app.entity.CardInfoBean;
import com.lianxi.dingtu.dingtu_urovo.app.entity.CardInfoTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.ReadCardTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.SimpleExpenseParam;
import com.lianxi.dingtu.dingtu_urovo.app.entity.SimpleExpenseTo;
import com.lianxi.dingtu.dingtu_urovo.app.listening.RFCardListening;
import com.lianxi.dingtu.dingtu_urovo.di.component.DaggerAutoComponent;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.AutoContract;
import com.lianxi.dingtu.dingtu_urovo.mvp.presenter.AutoPresenter;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.widget.CustomDialog;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.widget.LoadDialog;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.widget.MyAnimation;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.widget.PayDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 15:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AutoActivity extends BaseActivity<AutoPresenter> implements AutoContract.View {
    LoadDialog loadDialog;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.cost)
    EditText cost;
    @BindView(R.id.btn_print)
    Button btnPrint;
    SimpleExpenseParam param = new SimpleExpenseParam();
    private String name = "";
    StringBuilder printer = new StringBuilder();
    private String pwd = "";
    private int payCount = 0;

    int company = 0;
    int device = 0;
    private CardInfoBean cardInfoBean;
    private ReadCardUtils readCardUtils;
    private String key;

    private boolean isFirst = true;
    private boolean isPrint;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAutoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_auto; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("自动消费");
        setupWindowAnimations();

        loadDialog = LoadDialog.getInstance();
        loadDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.load_dialog);
        key = (String) SpUtils.get(this, AppConstant.NFC.NFC_KEY, "");

        String money = (String) SpUtils.get(this, AppConstant.Print.COST, "");
        if (!TextUtils.isEmpty(money)) {
            cost.setText(money);
            cost.setSelection(cost.getText().length());
        }

        company = UserInfoHelper.getInstance(this).getCode();
        String _device = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
        device = Integer.valueOf(TextUtils.isEmpty(_device) ? "1" : _device);
        cardInfoBean = new CardInfoBean();

        isPrint = (boolean) SpUtils.get(this, AppConstant.Receipt.isPrint, false);

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
                Log.e(TAG, "handleMessage: data:" + data);
                cardInfoBean = readCardUtils.cardInfoBean(data);
                Log.e(TAG, "handleMessage: cardInfoBean" + JSON.toJSONString(cardInfoBean));
                UserInfoHelper mUserInfoHelper = UserInfoHelper.getInstance(AutoActivity.this);
                if (TextUtils.isEmpty(cardInfoBean.getCode())) {
                    return;
                }
                if (TextUtils.equals("0000", cardInfoBean.getCode())) {
                    AudioUtils.getInstance().speakText("空白卡");
                    showMessage("空白卡");
                    return;
                }else if (Integer.parseInt(cardInfoBean.getCode(), 16) != mUserInfoHelper.getCode()) {
                    AudioUtils.getInstance().speakText("非本单位卡");
                    showMessage("非本单位卡");
                    return;
                }
                if (!isFirst) {
                    return;
                }
                SoundTool.getMySound(AutoActivity.this).playMusic("success");
                mPresenter.readtCardInfo(company, device, cardInfoBean.getNum());
            }

            @Override
            public void noFindRFCardListening() {
                isFirst=true;

            }

            @Override
            public void failReadRFCardListening() {
                isFirst = true;

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


    protected AntiShake util = new AntiShake();

    @OnClick(R.id.btn_print)
    public void onViewClicked() {
        if (util.check()) return;
        btnPrint.startAnimation(new MyAnimation());
        String print = (String) SpUtils.get(AutoActivity.this, AppConstant.Print.STUB2, "");
        PrinterUtils.getInstance(AutoActivity.this).printLianxi(new StringBuilder(print));

    }

    void createConsumerInfo(SimpleExpenseTo expenseTo) {
        final CustomDialog dialog = new CustomDialog(this, R.layout.pop_list, R.style.Dialog);
        RelativeLayout animContainer = dialog.findViewById(R.id.anim_container);
        AnimSpring.getInstance().startAnim(AdConstant.ANIM_UP_TO_DOWN, animContainer, AdConstant.BOUNCINESS, AdConstant.SPEED);
        TextView account = dialog.findViewById(R.id.name);
        TextView balance = dialog.findViewById(R.id.balance);
        TextView cost = dialog.findViewById(R.id.cost);
        account.setText(name);
        String amount = String.format("%.2f", expenseTo.getExpenseDetail().getBalance());
        SpannableStringBuilder builder = new SpannableStringBuilder(amount);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 27)), amount.indexOf("."), amount.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        balance.setText(builder);
        cost.setText(String.format("%.2f", expenseTo.getExpenseDetail().getAmount()));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        if (!dialog.isShowing())
            dialog.show();

        Flowable.just(1)
                .delay(7, TimeUnit.SECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Integer integer) throws Exception {
                        dialog.dismiss();
                    }
                });


    }

    PayDialog payDialog = null;

    void createPayDialog() {
        if (payDialog == null) {
            payDialog = new PayDialog(this);
            payDialog.setPasswordCallback(new PayDialog.PasswordCallback() {
                @Override
                public void callback(String password) {
//                if ("0000".equals(password)) {
//                    payDialog.clearPasswordText();
//                    Toasty.error(ManualActivity.this, "密码为错误，请重试", Toast.LENGTH_SHORT, true).show();
//
//                } else {
//                    Toast.makeText(ManualActivity.this, "密码为：" + password, Toast.LENGTH_SHORT).show();
//                    payDialog.dismiss();
//                }
                    pwd = password;
                    payDialog.dismiss();
                    String deviceID = (String) SpUtils.get(AutoActivity.this, AppConstant.Receipt.NO, "");
                    param.setNumber(cardInfoBean.getNum());
                    param.setAmount(Double.parseDouble(cost.getText().toString().trim()));
                    param.setDeviceID(Integer.valueOf(TextUtils.isEmpty(deviceID) ? "1" : deviceID));
                    param.setPayCount(payCount + 1);
                    param.setPayKey(pwd);
                    param.setPattern(2);
                    param.setDeviceType(2);
                    mPresenter.createSimpleExpense(param);
                }
            });
        }
        payDialog.clearPasswordText();
        payDialog.setMoney(cost.getText().toString().trim());
        payDialog.show();
        payDialog.setCancelable(false);
        payDialog.setCanceledOnTouchOutside(false);
    }

    private boolean checking() {
        if (TextUtils.isEmpty(cost.getText().toString().trim())) {
            Toast.makeText(this, "似乎您忘记输入消费金额了", Toast.LENGTH_LONG).show();
            return true;
        } else if (!DoubleUtil.isNumber(cost.getText().toString().trim())) {
            Toast.makeText(this, "消费金额不正确", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

//    @Override
//    public void onCardInfo(CardInfoTo cardInfoTo) {
//        param.setNumber(cardInfoTo.getNumber());
//        name = cardInfoTo.getName();
//        payCount = cardInfoTo.getPayCount();
//        mPresenter.getPaySgetPayKeySwitch2();
//    }

    @Override
    public void creatSuccess(SimpleExpenseTo simpleExpenseTo) {
        if (isPrint){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);

            printer.append("\n工作模式: 自动扣款");
            printer.append("\n姓    名: " + name);
            printer.append(String.format("\n折扣比率: %s", simpleExpenseTo.getExpenseDetail().getDiscountRate()));
            printer.append(String.format("\n实际消费: %.2f", simpleExpenseTo.getExpenseDetail().getAmount()));
            printer.append(String.format("\n账户余额: %.2f", simpleExpenseTo.getExpenseDetail().getBalance()));
            printer.append("\n" + str);

            PrinterUtils.getInstance(this).printLianxi(printer);
            SpUtils.put(this, AppConstant.Print.STUB2, printer.toString());
            printer.setLength(0);


        }
        payCount = -1;

        AudioUtils.getInstance().speakText("消费" + simpleExpenseTo.getExpenseDetail().getAmount() + "元");
        createConsumerInfo(simpleExpenseTo);
        Toasty.success(this, "消费成功", Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void creatBill(String str) {
        if (checking())
            return;
        if (payCount == -1) {
            showMessage("重新放置卡片");
            AudioUtils.getInstance().speakText("重新放置卡片");
            return;
        }
        if (TextUtils.equals("1", str)) {
            createPayDialog();
        } else {
            String deviceID = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
            param.setNumber(cardInfoBean.getNum());
            param.setAmount(Double.parseDouble(cost.getText().toString().trim()));
            param.setDeviceID(Integer.valueOf(TextUtils.isEmpty(deviceID) ? "1" : deviceID));
            param.setPayCount(payCount + 1);
            param.setPayKey(pwd);
            param.setPattern(2);
            param.setDeviceType(2);
            mPresenter.createSimpleExpense(param);
        }
    }

    @Override
    public void creatBill2(boolean isOpen) {
        if (checking())
            return;
        if (payCount == -1) {
            showMessage("重新放置卡片");
            AudioUtils.getInstance().speakText("重新放置卡片");
            return;
        }
        if (isOpen) {
            createPayDialog();
        } else {
            String deviceID = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
            param.setNumber(cardInfoBean.getNum());
            param.setAmount(Double.parseDouble(cost.getText().toString().trim()));
            param.setDeviceID(Integer.valueOf(TextUtils.isEmpty(deviceID) ? "1" : deviceID));
            param.setPayCount(payCount + 1);
            param.setPayKey(pwd);
            param.setPattern(2);
            param.setDeviceType(2);
            mPresenter.createSimpleExpense(param);
        }
    }

    @Override
    public void onReadCard(ReadCardTo readCardTo) {
        isFirst=false;
        param.setNumber(readCardTo.getNumber());
        name = readCardTo.getUserName();
        payCount = readCardTo.getPayCount();
        mPresenter.getPaySgetPayKeySwitch2();
    }

    @Override
    protected void onDestroy() {
        SpUtils.put(this, AppConstant.Print.COST, cost.getText().toString().trim());
        PrinterUtils.getInstance(this).close();
        readCardUtils.close();
        super.onDestroy();
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
}
