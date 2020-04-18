package com.lianxi.dingtu.dingtu_urovo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lianxi.dingtu.dingtu_urovo.di.module.ChoiceModule;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.ChoiceContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity.ChoiceActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 16:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ChoiceModule.class, dependencies = AppComponent.class)
public interface ChoiceComponent {
    void inject(ChoiceActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChoiceComponent.Builder view(ChoiceContract.View view);

        ChoiceComponent.Builder appComponent(AppComponent appComponent);

        ChoiceComponent build();
    }
}