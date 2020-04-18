package com.lianxi.dingtu.dingtu_urovo.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.RxUtils2;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_urovo.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_urovo.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_urovo.app.entity.DepositTo;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.DepositContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 15:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class DepositPresenter extends BasePresenter<DepositContract.Model, DepositContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    int index = 1;

    @Inject
    public DepositPresenter(DepositContract.Model model, DepositContract.View rootView) {
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

    public void setList(boolean isPullRefresh) {
        if (isPullRefresh) index = 1;
        String deviceID = (String) SpUtils.get(mApplication, AppConstant.Receipt.NO, "1");
        mModel.getDepositReport(index, 10, deviceID)
                .compose(RxUtils2.applySchedulers(mRootView, isPullRefresh))
                .subscribe(new Observer<BaseResponse<DepositTo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<DepositTo> depositToBaseResponse) {

                        if (depositToBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(depositToBaseResponse.getMessage());
                            Log.e(TAG, "onNext: " + depositToBaseResponse.getMessage());

                        } else {
                            if (depositToBaseResponse.isSuccess()) {
                                if (depositToBaseResponse.getContent() != null) {
                                    if (index == 1 && depositToBaseResponse.getContent().getRows() == null) {
                                        mRootView.noData();
                                    }
                                    if (depositToBaseResponse.getContent().getRows() != null) {
                                        mRootView.setData(depositToBaseResponse.getContent().getRows(), isPullRefresh);
                                        index++;
                                    }
                                }
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
