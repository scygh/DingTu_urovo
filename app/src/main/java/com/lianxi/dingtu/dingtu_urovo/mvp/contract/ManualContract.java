package com.lianxi.dingtu.dingtu_urovo.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.lianxi.dingtu.dingtu_urovo.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_urovo.app.entity.CardInfoTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.KeySwitchTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.ReadCardTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.SimpleExpenseParam;
import com.lianxi.dingtu.dingtu_urovo.app.entity.SimpleExpenseTo;

import io.reactivex.Observable;


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
public interface ManualContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {


        void onCardInfo(CardInfoTo cardInfoTo);

        void creatSuccess(SimpleExpenseTo simpleExpenseTo);

        void creatBill(String str);

        void creatBill2(boolean isOpen);

        void onReadCard(ReadCardTo readCardTo);

        void onFailed();    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<CardInfoTo>> getByNumber(int number);

        Observable<BaseResponse<SimpleExpenseTo>> createSimpleExpense(SimpleExpenseParam param);

        Observable<BaseResponse<String>> getPayKeySwitch(String key);

        Observable<BaseResponse<KeySwitchTo>> getEMDevice(int id);

        Observable<BaseResponse<ReadCardTo>> addReadCard(int companyCode, int deviceID,int number);
    }
}
