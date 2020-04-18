package com.lianxi.dingtu.dingtu_urovo.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.lianxi.dingtu.dingtu_urovo.mvp.contract.ClaimContract;
import com.lianxi.dingtu.dingtu_urovo.mvp.model.ClaimModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 09:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class ClaimModule {

    @Binds
    abstract ClaimContract.Model bindClaimModel(ClaimModel model);
}