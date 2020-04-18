package com.lianxi.dingtu.dingtu_urovo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lianxi.dingtu.dingtu_urovo.di.module.PaymentModule;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.PaymentContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity.PaymentActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 09:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PaymentModule.class, dependencies = AppComponent.class)
public interface PaymentComponent {
    void inject(PaymentActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PaymentComponent.Builder view(PaymentContract.View view);

        PaymentComponent.Builder appComponent(AppComponent appComponent);

        PaymentComponent build();
    }
}