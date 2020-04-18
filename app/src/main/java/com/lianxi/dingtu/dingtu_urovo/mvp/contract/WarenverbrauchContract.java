package com.lianxi.dingtu.dingtu_urovo.mvp.contract;

import com.iflytek.cloud.thirdparty.T;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.lianxi.dingtu.dingtu_urovo.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_urovo.app.entity.CardInfoTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.EMGoodsTo2;
import com.lianxi.dingtu.dingtu_urovo.app.entity.EMGoodsTypeTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.KeySwitchTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.ReadCardTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.SimpleExpenseParam;
import com.lianxi.dingtu.dingtu_urovo.app.entity.SimpleExpenseTo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;


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
public interface WarenverbrauchContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void onFailed();

        void onReadCard(ReadCardTo readCardTo);

        void creatBill2(boolean keyEnabled);

        void creatSuccess(SimpleExpenseTo simpleExpenseTo);

        void onEMGoodsDetailGet(List<EMGoodsTo2> content);

        void onPagers(List<EMGoodsTypeTo> emGoodsTypeToList);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<ReadCardTo>> addReadCard(int companyCode, int deviceID, int number);

        Observable<BaseResponse<KeySwitchTo>> getEMDevice(int id);

        Observable<BaseResponse<SimpleExpenseTo>> createSimpleExpense(SimpleExpenseParam param);

        Observable<BaseResponse<List<EMGoodsTypeTo>>> findEMGoodsType(String state);

        Observable<BaseResponse<List<EMGoodsTo2>>> findEMGoods(int index, int count, String goodsType);

    }
}
