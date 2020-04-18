package com.lianxi.dingtu.dingtu_urovo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lianxi.dingtu.dingtu_urovo.di.module.StepThreeModule;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.StepThreeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity.StepThreeActivity;


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
@Component(modules = StepThreeModule.class, dependencies = AppComponent.class)
public interface StepThreeComponent {
    void inject(StepThreeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        StepThreeComponent.Builder view(StepThreeContract.View view);

        StepThreeComponent.Builder appComponent(AppComponent appComponent);

        StepThreeComponent build();
    }
}