package com.lianxi.dingtu.dingtu_urovo.di.module;

import com.lianxi.dingtu.dingtu_urovo.mvp.contract.TodayContract;
import com.lianxi.dingtu.dingtu_urovo.mvp.model.TodayModel;

import dagger.Binds;
import dagger.Module;


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
@Module
public abstract class TodayModule {

    @Binds
    abstract TodayContract.Model bindTodayModel(TodayModel model);
}