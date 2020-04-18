package com.lianxi.dingtu.dingtu_urovo.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lianxi.dingtu.dingtu_urovo.app.api.UserService;
import com.lianxi.dingtu.dingtu_urovo.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_urovo.app.entity.DepartmentTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.RegisterParam;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.StepThreeContract;

import java.util.List;

import io.reactivex.Observable;


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
public class StepThreeModel extends BaseModel implements StepThreeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public StepThreeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override public Observable<BaseResponse<List<DepartmentTo>>> getDepartment() {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getDepartment();
    }

    @Override public Observable<BaseResponse<Integer>> getNextNumber() {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getNextNumber();
    }

    @Override public Observable<BaseResponse> addRegister(RegisterParam param) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).addRegister(param);
    }

}