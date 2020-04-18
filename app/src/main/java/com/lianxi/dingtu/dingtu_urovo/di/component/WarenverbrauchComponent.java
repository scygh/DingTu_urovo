package com.lianxi.dingtu.dingtu_urovo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lianxi.dingtu.dingtu_urovo.di.module.WarenverbrauchModule;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.WarenverbrauchContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity.WarenverbrauchActivity;


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
@Component(modules = WarenverbrauchModule.class, dependencies = AppComponent.class)
public interface WarenverbrauchComponent {
    void inject(WarenverbrauchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WarenverbrauchComponent.Builder view(WarenverbrauchContract.View view);

        WarenverbrauchComponent.Builder appComponent(AppComponent appComponent);

        WarenverbrauchComponent build();
    }
}