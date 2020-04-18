package com.lianxi.dingtu.dingtu_urovo.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lianxi.dingtu.dingtu_urovo.app.api.UserService;
import com.lianxi.dingtu.dingtu_urovo.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_urovo.app.entity.CardTypeTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.SubsidyTo;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.StepTwoContract;

import java.util.List;

import io.reactivex.Observable;


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
public class StepTwoModel extends BaseModel implements StepTwoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public StepTwoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override public Observable<BaseResponse<List<SubsidyTo>>> getSubsidyLeve() {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getSubsidyLeve();
    }

    @Override public Observable<BaseResponse<List<CardTypeTo>>> getCardType() {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getCardType();
    }

    @Override public Observable<BaseResponse<Double>> getDonate(int cardType, double amount) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getDonate(cardType, amount);
    }

    @Override public Observable<BaseResponse<String>> getPayKeySwitch(String key) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getPayKeySwitch(key);
    }

}