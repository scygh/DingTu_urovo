package com.lianxi.dingtu.dingtu_urovo.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.lianxi.dingtu.dingtu_urovo.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_urovo.app.entity.CardTypeTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.SubsidyTo;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 09:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface StepTwoContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void onChoiceType(List<CardTypeTo> list);

        void onChoiceLeve(List<SubsidyTo> list);

        void onDonate(double d);

        void onDonateSwitch(String str);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<List<SubsidyTo>>> getSubsidyLeve();

        Observable<BaseResponse<List<CardTypeTo>>> getCardType();

        Observable<BaseResponse<Double>> getDonate(int cardType, double amount);

        Observable<BaseResponse<String>> getPayKeySwitch(String key);
    }
}
