package com.lianxi.dingtu.dingtu_urovo.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.lianxi.dingtu.dingtu_urovo.app.api.UserService;
import com.lianxi.dingtu.dingtu_urovo.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_urovo.app.entity.CardInfoTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.MoneyParam;
import com.lianxi.dingtu.dingtu_urovo.app.entity.ReadCardTo;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.CloseContract;

import javax.inject.Inject;

import io.reactivex.Observable;


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
public class CloseModel extends BaseModel implements CloseContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CloseModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
    @Override public Observable<BaseResponse<CardInfoTo>> getByNumber(int number) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getByNumber(number);
    }

    @Override public Observable<BaseResponse> addDeregister(MoneyParam param) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).addDeregister(param);
    }

    @Override
    public Observable<BaseResponse<ReadCardTo>> addReadCard(int companyCode, int deviceID, int number) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).addReadCard(companyCode, deviceID, number);
    }
}