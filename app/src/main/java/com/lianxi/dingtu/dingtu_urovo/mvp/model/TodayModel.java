package com.lianxi.dingtu.dingtu_urovo.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.lianxi.dingtu.dingtu_urovo.app.api.UserService;
import com.lianxi.dingtu.dingtu_urovo.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_urovo.app.entity.AggregateTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.MachineAmountTo;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.TodayContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


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
public class TodayModel extends BaseModel implements TodayContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TodayModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
    public Observable<BaseResponse<MachineAmountTo>> getMachineAmount(int deviceId) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getMachineAmount(deviceId);
    }
    @Override
    public Observable<BaseResponse<MachineAmountTo>> getMachineTimeCount(int deviceId) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getMachineTimeCount(deviceId);
    }
}