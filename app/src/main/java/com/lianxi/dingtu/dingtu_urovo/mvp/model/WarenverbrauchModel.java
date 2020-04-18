package com.lianxi.dingtu.dingtu_urovo.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lianxi.dingtu.dingtu_urovo.app.api.UserService;
import com.lianxi.dingtu.dingtu_urovo.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_urovo.app.entity.CardInfoTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.EMGoodsTo2;
import com.lianxi.dingtu.dingtu_urovo.app.entity.EMGoodsTypeTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.KeySwitchTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.ReadCardTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.SimpleExpenseParam;
import com.lianxi.dingtu.dingtu_urovo.app.entity.SimpleExpenseTo;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.WarenverbrauchContract;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/28/2019 11:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class WarenverbrauchModel extends BaseModel implements WarenverbrauchContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WarenverbrauchModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override public Observable<BaseResponse<List<EMGoodsTypeTo>>> findEMGoodsType(String state) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).findEMGoodsType(state);
    }

    @Override public Observable<BaseResponse<List<EMGoodsTo2>>> findEMGoods(int index, int count, String goodsType) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).findEMGoods(index, count, goodsType);
    }

    @Override public Observable<BaseResponse<ReadCardTo>> addReadCard(int companyCode, int deviceID, int number) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).addReadCard(companyCode, deviceID, number);
    }

    @Override
    public Observable<BaseResponse<KeySwitchTo>> getEMDevice(int id) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getEMDevice(id);
    }

    @Override
    public Observable<BaseResponse<SimpleExpenseTo>> createSimpleExpense(SimpleExpenseParam param) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).createSimpleExpense(param);
    }

}