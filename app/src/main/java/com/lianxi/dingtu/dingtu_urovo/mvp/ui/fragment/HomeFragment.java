package com.lianxi.dingtu.dingtu_urovo.mvp.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.ai.edge.core.util.FileUtil;
import com.google.gson.Gson;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.dingtu_urovo.R;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.FillUtil;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.UserInfoHelper;
import com.lianxi.dingtu.dingtu_urovo.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_urovo.app.entity.HomeIndex;
import com.lianxi.dingtu.dingtu_urovo.di.component.DaggerHomeComponent;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.HomeContract;
import com.lianxi.dingtu.dingtu_urovo.mvp.presenter.HomePresenter;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity.AutoActivity;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity.CloseActivity;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity.DepositActivity;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity.ExpenseActivity;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity.FaceActivity;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity.ManualActivity;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity.OpenActivity;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity.QRDepositActivity;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity.RechargeActivity;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity.RefundActivity;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity.TodayActivity;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity.WarenverbrauchActivity;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.widget.HollowTextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/08/2019 14:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {
    @BindView(R.id.company_code)
    HollowTextView companyCode;
    @BindView(R.id.account)
    HollowTextView account;
    private ArrayList<RelativeLayout> mLayout_List = new ArrayList<>();
    @BindView(R.id.ll_content)
    LinearLayout ll_content;


    private String name = "";
    private String version = "";
    private String ak;
    private String sk;
    private String apiUrl;
    private String soc;
    private ArrayList<String> socList = new ArrayList<>();
    private int type;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        account.setText(String.format(" %s", UserInfoHelper.getInstance(getActivity()).getUserInfoTo().getAccount()));
        companyCode.setText(String.format("单位代码 %s", UserInfoHelper.getInstance(getActivity()).getCode()));
        boolean BLOCK_USER = (boolean) SpUtils.get(getContext(), AppConstant.Api.BLOCK_USER, true);
        boolean BLOCK_REPORT = (boolean) SpUtils.get(getContext(), AppConstant.Api.BLOCK_REPORT, true);

        addDisposabe(getData(new DisposableObserver<HomeIndex>() {
            @Override
            public void onNext(HomeIndex homeIndex) {
                List<HomeIndex.Pub_Dictionary_Item> items = homeIndex.pub_Dictionary_Item;
                if (items != null && items.size() > 0)
                    for (HomeIndex.Pub_Dictionary_Item item : items) {
                        if (!BLOCK_USER&&item.CHAR1.equals("用户管理"))continue;
                        if (!BLOCK_REPORT&&item.CHAR1.equals("报表中心"))continue;
                        addItem(item);
                    }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "onError: " + e);
            }

            @Override
            public void onComplete() {

            }
        }, HomeIndex.class, "Pub_Dictionary_Item.txt"));


        initConfig();
    }

    /**
     * 读取json配置
     */
    private void initConfig() {
        try {
            String configJson = FileUtil.readAssetFileUtf8String(getActivity().getAssets(), "demo/config.json");
            JSONObject jsonObject = new JSONObject(configJson);
            name = jsonObject.getString("model_name");
            version = jsonObject.getString("model_version");
            type = jsonObject.getInt("model_type");

            if (jsonObject.has("apiUrl")) {
                apiUrl = jsonObject.getString("apiUrl");
                ak = jsonObject.getString("ak");
                sk = jsonObject.getString("sk");
            }

            JSONArray jsonArray = jsonObject.getJSONArray("soc");
            for (int i = 0; i < jsonArray.length(); i++) {
                String s = jsonArray.getString(i);
                socList.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    private CompositeDisposable disposables;

    public void addDisposabe(Disposable disposable) {
        if (disposables == null) {
            disposables = new CompositeDisposable();
        }
        disposables.add(disposable);

    }

    public <S> Disposable getData(DisposableObserver<S> consumer, final Class<S> clazz, final String fillName) {
        return Observable.create(new ObservableOnSubscribe<S>() {
            @Override
            public void subscribe(ObservableEmitter<S> e) throws Exception {
                InputStream is = getContext().getAssets().open(fillName);
                String text = FillUtil.readTextFromFile(is);
                Gson gson = new Gson();
                e.onNext(gson.fromJson(text, clazz));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(consumer);
    }


    public void addItem(HomeIndex.Pub_Dictionary_Item item) {
        if (item == null) {
            return;
        }

        int i;
        View layout = null;
        LinearLayout.LayoutParams ll_params;
        GridLayout.LayoutParams gl_params;
        RelativeLayout.LayoutParams rl_params;
        //获取单位工具类，每个菜单的上空隙
        int margin = ArmsUtils.dip2px(getActivity(), 10);
        for (i = 0; i < ll_content.getChildCount(); i++) {

            //找到对应的类型
            String tag = TextUtils.isEmpty((String) ll_content.getChildAt(i).getTag()) ? "" : (String) ll_content.getChildAt(i).getTag();
            String char1 = TextUtils.isEmpty(item.CHAR1) ? "" : item.CHAR1;
            if (tag.equals(char1)) {

                layout = ll_content.getChildAt(i);
            }
        }
        //创建类型块
        if (layout == null) {
            layout = getActivity().getLayoutInflater().inflate(R.layout.listitem_work, null);
            ((TextView) layout.findViewById(R.id.tv_title)).setText(item.CHAR1);
            layout.setTag(item.CHAR1);
            ll_content.addView(layout);
            ll_params = (LinearLayout.LayoutParams) layout.getLayoutParams();
            ll_params.setMargins(0, margin, 0, 0);
            layout.setLayoutParams(ll_params);
        }
        GridLayout gl_content = layout.findViewById(R.id.gl_content);
        RelativeLayout item_layout = new RelativeLayout(getActivity());
        item_layout.setPadding(0, ArmsUtils.dip2px(getActivity(), 7), 0, ArmsUtils.dip2px(getActivity(), 10));
        gl_content.addView(item_layout);
        gl_params = (GridLayout.LayoutParams) item_layout.getLayoutParams();
        gl_params.width = getWidth(1.0f) / 4;
        item_layout.setLayoutParams(gl_params);
        item_layout.setTag(item);
        item_layout.setOnClickListener(MenuItem_Click);
        mLayout_List.add(item_layout);

        ImageView image = new ImageView(getActivity());
        image.setId(R.id.sy_image);
        if (TextUtils.equals("1", item.ITEM_CODE)) {
            image.setImageResource(R.mipmap.sdxf);
        } else if (TextUtils.equals("2", item.ITEM_CODE)) {
            image.setImageResource(R.mipmap.zdxf);
        } else if (TextUtils.equals("3", item.ITEM_CODE)) {
            image.setImageResource(R.mipmap.wkxf);
        } else if (TextUtils.equals("4", item.ITEM_CODE)) {
            image.setImageResource(R.mipmap.cprk);
        } else if (TextUtils.equals("5", item.ITEM_CODE)) {
            image.setImageResource(R.mipmap.icon_kh);
        } else if (TextUtils.equals("6", item.ITEM_CODE)) {
            image.setImageResource(R.mipmap.icon_cz);
        } else if (TextUtils.equals("7", item.ITEM_CODE)) {
            image.setImageResource(R.mipmap.icon_tk);
        } else if (TextUtils.equals("8", item.ITEM_CODE)) {
            image.setImageResource(R.mipmap.icon_zx);
        } else if (TextUtils.equals("9", item.ITEM_CODE)) {
            image.setImageResource(R.mipmap.smcz);
        } else if (TextUtils.equals("10", item.ITEM_CODE)) {
            image.setImageResource(R.mipmap.drsy);
        } else if (TextUtils.equals("11", item.ITEM_CODE)) {
            image.setImageResource(R.mipmap.czbb);
        } else if (TextUtils.equals("12", item.ITEM_CODE)) {
            image.setImageResource(R.mipmap.xfbb);
        }
        item_layout.addView(image);
        rl_params = (RelativeLayout.LayoutParams) image.getLayoutParams();
        rl_params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        rl_params.topMargin = ArmsUtils.dip2px(getActivity(), 3);
        image.setLayoutParams(rl_params);

        TextView tv_text = new TextView(getActivity());
        tv_text.setText(item.ITEM_NAME);
        tv_text.setTextSize(16);
        item_layout.addView(tv_text);
        rl_params = (RelativeLayout.LayoutParams) tv_text.getLayoutParams();
        rl_params.addRule(RelativeLayout.BELOW, image.getId());
        rl_params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        tv_text.setLayoutParams(rl_params);


    }

    View.OnClickListener MenuItem_Click = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            HomeIndex.Pub_Dictionary_Item item = (HomeIndex.Pub_Dictionary_Item) v.getTag();
            boolean checkChip = checkChip();
            if (item != null) {
                Intent intent = new Intent();
                if (item.ITEM_CODE.equals("1")) {
                    intent.setClass(getActivity(), ManualActivity.class);
                    getActivity().startActivity(intent);
                } else if (item.ITEM_CODE.equals("2")) {
                    intent.setClass(getActivity(), AutoActivity.class);
                    getActivity().startActivity(intent);
                } else if (item.ITEM_CODE.equals("3")) {
                    intent.setClass(getActivity(), FaceActivity.class);
                    getActivity().startActivity(intent);
                } else if (item.ITEM_CODE.equals("4")) {
                    intent.setClass(getActivity(), WarenverbrauchActivity.class);
                    getActivity().startActivity(intent);
                } else if (item.ITEM_CODE.equals("5")) {
                    intent.setClass(getActivity(), OpenActivity.class);
                    getActivity().startActivity(intent);
                } else if (item.ITEM_CODE.equals("6")) {
                    intent.setClass(getActivity(), RechargeActivity.class);
                    getActivity().startActivity(intent);
                } else if (item.ITEM_CODE.equals("7")) {
                    intent.setClass(getActivity(), RefundActivity.class);
                    getActivity().startActivity(intent);
                } else if (item.ITEM_CODE.equals("8")) {
                    intent.setClass(getActivity(), CloseActivity.class);
                    getActivity().startActivity(intent);
                } else if (item.ITEM_CODE.equals("9")) {
                    intent.setClass(getActivity(), QRDepositActivity.class);
                    getActivity().startActivity(intent);
                } else if (item.ITEM_CODE.equals("10")) {
                    intent.setClass(getActivity(), TodayActivity.class);
                    getActivity().startActivity(intent);
                } else if (item.ITEM_CODE.equals("11")) {
                    intent.setClass(getActivity(), DepositActivity.class);
                    getActivity().startActivity(intent);
                } else if (item.ITEM_CODE.equals("12")) {
                    intent.setClass(getActivity(), ExpenseActivity.class);
                    getActivity().startActivity(intent);
                } else {
                    showMessage("该模块还在实现中...请期待新版本");
                }
            }
        }
    };

    public int getWidth(float scale) {
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        return (int) (display.getWidth() * scale);
    }


    private boolean checkChip() {
        if (socList.contains("dsp") && Build.HARDWARE.equalsIgnoreCase("qcom")) {
            soc = "dsp";
            return true;
        }
        if (socList.contains("npu") && (Build.HARDWARE.contains("kirin970") || Build.HARDWARE.contains("kirin980"))) {
            soc = "npu";
            return true;
        }
        if (socList.contains("arm")) {
            soc = "arm";
            return true;
        }
        return false;
    }
}
