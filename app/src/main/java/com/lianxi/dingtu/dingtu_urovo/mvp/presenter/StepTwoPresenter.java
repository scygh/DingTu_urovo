package com.lianxi.dingtu.dingtu_urovo.mvp.presenter;

import android.app.Application;

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

import com.lianxi.dingtu.dingtu_urovo.app.Utils.RxUtils;
import com.lianxi.dingtu.dingtu_urovo.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_urovo.app.entity.CardTypeTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.SubsidyTo;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.StepTwoContract;

import java.util.List;


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
@ActivityScope
public class StepTwoPresenter extends BasePresenter<StepTwoContract.Model, StepTwoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public StepTwoPresenter(StepTwoContract.Model model, StepTwoContract.View rootView) {
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

    public void onSubsidyLeve() {
        mModel.getSubsidyLeve()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<List<SubsidyTo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<SubsidyTo>> listBaseResponse) {
                        if (listBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(listBaseResponse.getMessage());
                        } else {
                            if (listBaseResponse.isSuccess())
                                if (listBaseResponse.getContent() != null)
                                    mRootView.onChoiceLeve(listBaseResponse.getContent());
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

    public void onCardType() {
        mModel.getCardType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<CardTypeTo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<CardTypeTo>> listBaseResponse) {
                        if (listBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(listBaseResponse.getMessage());
                        } else {
                            if (listBaseResponse.isSuccess())
                                if (listBaseResponse.getContent() != null)
                                    mRootView.onChoiceType(listBaseResponse.getContent());
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

    public void onDonate(int id, double d) {
        mModel.getDonate(id, d)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<Double>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<Double> doubleBaseResponse) {
                        if (doubleBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(doubleBaseResponse.getMessage());
                        } else {
                            if (doubleBaseResponse.isSuccess())
                                mRootView.onDonate(doubleBaseResponse.getContent());
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

    public void onDonateSwitch() {
        mModel.getPayKeySwitch("DonateSwitch")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<String> booleanBaseResponse) {
                        if (booleanBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(booleanBaseResponse.getMessage());
                        } else {
                            if (booleanBaseResponse.isSuccess())
                                mRootView.onDonateSwitch(booleanBaseResponse.getContent());
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
