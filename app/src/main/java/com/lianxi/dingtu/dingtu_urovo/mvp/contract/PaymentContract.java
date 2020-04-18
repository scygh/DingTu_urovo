package com.lianxi.dingtu.dingtu_urovo.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.lianxi.dingtu.dingtu_urovo.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_urovo.app.entity.QRExpenseParam;
import com.lianxi.dingtu.dingtu_urovo.app.entity.QRExpenseTo;
import com.lianxi.dingtu.dingtu_urovo.app.entity.QRReadTo;

import io.reactivex.Observable;


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
public interface PaymentContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void onPaySuccess(QRExpenseTo qrExpenseTo);

        void onPayFailure();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<QRReadTo>> getQRRead(String qrCodeContent, int deviceID);

        Observable<BaseResponse<QRExpenseTo>> addQRExpense(QRExpenseParam param);
    }
}
