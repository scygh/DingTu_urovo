package com.lianxi.dingtu.dingtu_urovo.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.RxUtils;
import com.lianxi.dingtu.dingtu_urovo.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_urovo.app.entity.CardInfoTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.MoneyParam;
import com.lianxi.dingtu.dingtu_urovo.app.entity.ReadCardTo;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.CloseContract;

import javax.inject.Inject;

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
public class ClosePresenter extends BasePresenter<CloseContract.Model, CloseContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ClosePresenter(CloseContract.Model model, CloseContract.View rootView) {
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

    public void readtCardInfo(int company, int id, int number) {
        mModel.addReadCard(company, id, number)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<ReadCardTo>>() {
                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<ReadCardTo> readCardToBaseResponse) {
                        Log.e(TAG, "onNext: " + JSON.toJSONString(readCardToBaseResponse));

                        if (readCardToBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(readCardToBaseResponse.getMessage());
                        } else {
                            if (readCardToBaseResponse.isSuccess())
                                if (readCardToBaseResponse.getContent() != null)
                                    mRootView.onReadCard(readCardToBaseResponse.getContent());
                        }
                    }

                });
    }

    public void getCardInfo(int number) {
        mModel.getByNumber(number)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<CardInfoTo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<CardInfoTo> cardInfoToBaseResponse) {
                        if (cardInfoToBaseResponse.getStatusCode() != 200) {

                        } else {
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

    public void onDeregister(MoneyParam param) {
        mModel.addDeregister(param)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(baseResponse.getMessage());
                        } else {
                            if (baseResponse.isSuccess())
                                if (baseResponse.isResult())
                                    mRootView.onDeregister();
                                else
                                    mRootView.showMessage(baseResponse.getMessage());
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
}
