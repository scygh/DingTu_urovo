package com.lianxi.dingtu.dingtu_urovo.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.RxUtils;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_urovo.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_urovo.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_urovo.app.entity.AggregateTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.MachineAmountTo;
import com.lianxi.dingtu.dingtu_urovo.app.event.ReplyEvent;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.TodayContract;

import org.simple.eventbus.EventBus;

import java.text.Format;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import timber.log.Timber;


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
public class TodayPresenter extends BasePresenter<TodayContract.Model, TodayContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public TodayPresenter(TodayContract.Model model, TodayContract.View rootView) {
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

    public void getMachineAmount(){
        Integer deviceID = Integer.parseInt((String) SpUtils.get(mApplication, AppConstant.Receipt.NO, "1"));

        mModel.getMachineAmount(deviceID)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<MachineAmountTo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<MachineAmountTo> machineAmountToBaseResponse) {
                        if (machineAmountToBaseResponse.isSuccess()){
                            mRootView.MachineAmount(machineAmountToBaseResponse.getContent());
                            EventBus.getDefault().post(new ReplyEvent(1));
                            Log.e(TAG, "MachineAmount: 成功" );

                        }
                    }
                });
    }
    public void getMachineTimeCount(){
        Integer deviceID = Integer.parseInt((String) SpUtils.get(mApplication, AppConstant.Receipt.NO, "1"));

        mModel.getMachineTimeCount(deviceID)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<MachineAmountTo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<MachineAmountTo> machineAmountToBaseResponse) {
                        if (machineAmountToBaseResponse.isSuccess()){
                            mRootView.MachineTimeCount(machineAmountToBaseResponse.getContent());
                            EventBus.getDefault().post(new ReplyEvent(1));
                            Log.e(TAG, "MachineTimeCount: 成功" );

                        }
                    }
                });
    }
}
