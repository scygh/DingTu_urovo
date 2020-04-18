package com.lianxi.dingtu.dingtu_urovo.mvp.presenter;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.AudioUtils;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.RxUtils;
import com.lianxi.dingtu.dingtu_urovo.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_urovo.app.entity.CardInfoTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.MoneyParam;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.RefundContract;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


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
@ActivityScope
public class RefundPresenter extends BasePresenter<RefundContract.Model, RefundContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public RefundPresenter(RefundContract.Model model, RefundContract.View rootView) {
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

    public void getCardInfo(int number) {
        mModel.getByNumber(number)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<CardInfoTo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse<CardInfoTo> cardInfoToBaseResponse) {
                        if (cardInfoToBaseResponse.getStatusCode()!=200){
                            mRootView.showMessage(cardInfoToBaseResponse.getMessage());
                        }else {
                            if (cardInfoToBaseResponse.isSuccess())
                                if (cardInfoToBaseResponse.getContent() != null)
                                    mRootView.onCardInfo(cardInfoToBaseResponse.getContent());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void onRefund(MoneyParam param) {
        mModel.addRefund(param)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.getStatusCode()!=200){
                            mRootView.showMessage(baseResponse.getMessage());
                        }else {
                            if (baseResponse.isSuccess())
                                if (baseResponse.isResult()){
                                    getCardInfo(param.getNumber());
                                    AudioUtils.getInstance().speakText(String.format("退款%.2f元",param.getAmount()));
                                    Toasty.success(mApplication, "退款成功", Toast.LENGTH_SHORT, true).show();
                                }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: "+e );
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
