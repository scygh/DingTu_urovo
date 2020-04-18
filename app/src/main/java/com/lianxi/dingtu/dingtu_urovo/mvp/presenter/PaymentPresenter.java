package com.lianxi.dingtu.dingtu_urovo.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.jess.arms.utils.RxLifecycleUtils;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_urovo.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_urovo.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_urovo.app.entity.QRExpenseParam;
import com.lianxi.dingtu.dingtu_urovo.app.entity.QRExpenseTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.QRReadTo;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.PaymentContract;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 09:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class PaymentPresenter extends BasePresenter<PaymentContract.Model, PaymentContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public PaymentPresenter(PaymentContract.Model model, PaymentContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void onScanQR(String str, double dou) {
        String deviceID = (String) SpUtils.get(mApplication, AppConstant.Receipt.NO, "");
        int id = Integer.valueOf(TextUtils.isEmpty(deviceID) ? "1" : deviceID);
        mModel.getQRRead(str, id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Observer<BaseResponse<QRReadTo>>() {
                    @Override
                    public void onError(Throwable t) {
                        mRootView.hideLoading();
                        mRootView.onPayFailure();
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<QRReadTo> qrReadToBaseResponse) {
                        if (qrReadToBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(qrReadToBaseResponse.getMessage());
                            mRootView.onPayFailure();
                        } else {
                            if (qrReadToBaseResponse.isSuccess()) {
                                QRExpenseParam param = new QRExpenseParam();
                                param.setQRContent(str);
                                param.setNumber(qrReadToBaseResponse.getContent().getNumber());
                                param.setAmount(dou);
                                param.setPattern(1);
                                param.setPayCount(qrReadToBaseResponse.getContent().getPayCount());
                                param.setDeviceID(id);
                                param.setDeviceType(2);
                                param.setQRType(qrReadToBaseResponse.getContent().getQRType());
                                onExpenseQR(param);
                            }
                        }

                    }
                });
    }

    private void onExpenseQR(QRExpenseParam param) {
        mModel.addQRExpense(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Observer<BaseResponse<QRExpenseTo>>() {
                    @Override
                    public void onError(Throwable t) {
                        mRootView.onPayFailure();
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<QRExpenseTo> qrExpenseToBaseResponse) {
                        if (qrExpenseToBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(qrExpenseToBaseResponse.getMessage());
                            mRootView.onPayFailure();

                        } else {
                            if (qrExpenseToBaseResponse.isSuccess())
                                mRootView.onPaySuccess(qrExpenseToBaseResponse.getContent());
                        }

                    }
                });
    }
}
