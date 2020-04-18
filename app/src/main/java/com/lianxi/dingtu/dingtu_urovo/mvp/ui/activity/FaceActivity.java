package com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity2;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.dingtu_urovo.R;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.AntiShake;
import com.lianxi.dingtu.dingtu_urovo.di.component.DaggerFaceComponent;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.FaceContract;
import com.lianxi.dingtu.dingtu_urovo.mvp.presenter.FacePresenter;


import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 15:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class FaceActivity extends BaseActivity<FacePresenter> implements FaceContract.View {
    @BindView(R.id.root) LinearLayout root;
    @BindView(R.id.cost) EditText cost;
    AntiShake util = new AntiShake();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFaceComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_face; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("当面付");
        setupWindowAnimations();
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

    private static final int SCAN_REQUEST_CODE = 100;

    private void startScanActivity() {
        Intent intent = new Intent(FaceActivity.this, CaptureActivity2.class);
        intent.putExtra(CaptureActivity2.USE_DEFUALT_ISBN_ACTIVITY, true);
        startActivityForResult(intent, SCAN_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SCAN_REQUEST_CODE) {
                //Todo Handle the isbn number entered manually
                String isbn = data.getStringExtra("CaptureIsbn");
                if (!TextUtils.isEmpty(isbn)) {
                    //todo something
                    Log.e(TAG, "onActivityResult: " + isbn);
                    Intent intent = new Intent();
                    intent.putExtra("content", isbn);
                    intent.putExtra("amount", Double.valueOf(cost.getText().toString().trim()));
                    intent.setClass(FaceActivity.this, PaymentActivity.class);
                    startActivity(intent);
                }
            }
        }
    }


    @OnClick(R.id.confirm) public void onViewClicked() {
        if (util.check()) return;
        if (TextUtils.isEmpty(cost.getText().toString().trim())) {
            Toasty.error(FaceActivity.this, "请先输入消费金额", Toast.LENGTH_SHORT, true).show();
            return;
        }
        startScanActivity();
    }
}
