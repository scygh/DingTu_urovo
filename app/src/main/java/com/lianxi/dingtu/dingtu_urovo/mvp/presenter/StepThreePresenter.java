package com.lianxi.dingtu.dingtu_urovo.mvp.presenter;

import android.app.Application;
import android.util.Log;

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
import com.lianxi.dingtu.dingtu_urovo.app.entity.DepartmentTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.RegisterParam;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.StepThreeContract;

import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 09:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class StepThreePresenter extends BasePresenter<StepThreeContract.Model, StepThreeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public StepThreePresenter(StepThreeContract.Model model, StepThreeContract.View rootView) {
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

    public void onDepartment() {
        mModel.getDepartment()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<List<DepartmentTo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse<List<DepartmentTo>> listBaseResponse) {
                        if (listBaseResponse.getStatusCode()!=200){
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }else {
                            if (listBaseResponse.isSuccess())
                                if (listBaseResponse.getContent() != null)
                                    mRootView.onDepartment(listBaseResponse.getContent());
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

    public void onRegister(RegisterParam param) {
        mModel.addRegister(param)
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
                                mRootView.onRegister();
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

    public void getNextNumber(){
        mModel.getNextNumber()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse<Integer> integerBaseResponse) {
                        if (integerBaseResponse.getStatusCode()!=200) {
                            mRootView.showMessage(integerBaseResponse.getMessage());

                        }else {
                            if(integerBaseResponse.isSuccess())
                                mRootView.onNextNumber(integerBaseResponse.getContent());
                            Log.e(TAG, "onNext: "+ integerBaseResponse.getContent());

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
