package com.lianxi.dingtu.dingtu_urovo.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.dingtu_urovo.di.module.AutoModule;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.AutoContract;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity.AutoActivity;


import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 15:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AutoModule.class, dependencies = AppComponent.class)
public interface AutoComponent {
    void inject(AutoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AutoComponent.Builder view(AutoContract.View view);

        AutoComponent.Builder appComponent(AppComponent appComponent);

        AutoComponent build();
    }
}