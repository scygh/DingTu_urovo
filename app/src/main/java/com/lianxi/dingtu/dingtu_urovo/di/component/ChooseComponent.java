package com.lianxi.dingtu.dingtu_urovo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lianxi.dingtu.dingtu_urovo.di.module.ChooseModule;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.ChooseContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity.ChooseActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/21/2019 15:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ChooseModule.class, dependencies = AppComponent.class)
public interface ChooseComponent {
    void inject(ChooseActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChooseComponent.Builder view(ChooseContract.View view);

        ChooseComponent.Builder appComponent(AppComponent appComponent);

        ChooseComponent build();
    }
}