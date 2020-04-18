package com.lianxi.dingtu.dingtu_urovo.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.RxUtils2;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_urovo.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_urovo.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_urovo.app.entity.ExpenseTo;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.ExpenseContract;

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
public class ExpensePresenter extends BasePresenter<ExpenseContract.Model, ExpenseContract.View> {
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
    public ExpensePresenter(ExpenseContract.Model model, ExpenseContract.View rootView) {
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
        mModel.getExpenseReport(index, 10, deviceID)
                .compose(RxUtils2.applySchedulers(mRootView, isPullRefresh))
                .subscribe(new Observer<BaseResponse<ExpenseTo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<ExpenseTo> expenseToBaseResponse) {
                        if (expenseToBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(expenseToBaseResponse.getMessage());
                        } else {
                            if (expenseToBaseResponse.isSuccess()) {
                                if (index == 1 && expenseToBaseResponse.getContent().getRows() == null) {
                                    mRootView.noData();
                                }
                                if (expenseToBaseResponse.getContent().getRows() != null) {
                                    mRootView.setData(expenseToBaseResponse.getContent().getRows(), isPullRefresh);
                                    index++;
                                }
                            }
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
