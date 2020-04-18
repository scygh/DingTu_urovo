package com.lianxi.dingtu.dingtu_urovo.mvp.ui.fragment;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.dingtu_urovo.R;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.DataCleanManager;
import com.lianxi.dingtu.dingtu_urovo.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_urovo.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_urovo.app.task.MainTask;
import com.lianxi.dingtu.dingtu_urovo.app.task.TaskParams;
import com.lianxi.dingtu.dingtu_urovo.di.component.DaggerMineComponent;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.MineContract;
import com.lianxi.dingtu.dingtu_urovo.mvp.presenter.MinePresenter;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.widget.AboutDialog;


import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static android.content.Context.AUDIO_SERVICE;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/08/2019 14:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View {
    @BindView(R.id.mac_num)
    TextView macNum;
    @BindView(R.id.rl_mac)
    RelativeLayout rlMac;
    @BindView(R.id.switch_print)
    Switch switchPrint;
    @BindView(R.id.tv_head) TextView tvHead;
    @BindView(R.id.tv_address) TextView tvAddress;
    @BindView(R.id.tv_phone) TextView tvPhone;
    @BindView(R.id.bar)
    SeekBar sbVolume;
    @BindView(R.id.cache) TextView cache;
    @BindView(R.id.version) TextView version;
    @BindView(R.id.exit) TextView exit;
    //    @BindView(R.id.tv_max_size) TextView tv_max_size;
    private int maxVolume, currentVolume;
    private int pic_max_size = 1000;
    private int which_one = 0;
    final String items[] = {"100", "200", "300", "400", "500", "600", "700", "800", "900", "1000"};

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMineComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        boolean isPrint = (boolean) SpUtils.get(getActivity(), AppConstant.Receipt.isPrint, false);
        switchPrint.setChecked(isPrint);
        switchPrint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SpUtils.put(getActivity(), AppConstant.Receipt.isPrint, b);
            }
        });
        String no = (String) SpUtils.get(getActivity(), AppConstant.Receipt.NO, "");
        if (!TextUtils.isEmpty(no)) {
            macNum.setText(no);
        }
        String name = (String) SpUtils.get(getActivity(), AppConstant.Receipt.NAME, "");
        if (!TextUtils.isEmpty(name)) {
            tvHead.setText(name);
        }
        String address = (String) SpUtils.get(getActivity(), AppConstant.Receipt.ADDRESS, "");
        if (!TextUtils.isEmpty(address)) {
            tvAddress.setText(address);
        }
        String phone = (String) SpUtils.get(getActivity(), AppConstant.Receipt.PHONE, "");
        if (!TextUtils.isEmpty(phone)) {
            tvPhone.setText(phone);
        }
        //初始化音频管理器
        AudioManager mAudioManager = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);

        //获取系统最大音量
        maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        // 获取设备当前音量
        currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        // 设置seekbar的最大值
        sbVolume.setMax(maxVolume);

        // 显示音量
        sbVolume.setProgress(currentVolume);

        sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);  //获取当前值
                sbVolume.setProgress(currentVolume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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
    @OnClick({R.id.mac_num, R.id.rl_mac, R.id.switch_print, R.id.tv_head, R.id.tv_address, R.id.tv_phone, R.id.bar, R.id.cache, R.id.version, R.id.exit}) public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mac_num:
                new MaterialDialog.Builder(getActivity())
                        .title("修改机器号")
                        .inputType(InputType.TYPE_CLASS_PHONE)
                        .positiveText("确定")
                        .positiveColorRes(R.color.colorPrimaryText)
                        .negativeText("取消")
                        .negativeColorRes(R.color.colorPrimaryText)
                        .input(
                                "",
                                macNum.getText().toString().trim(),
                                false,
                                (dialog, input) -> {
                                    macNum.setText(input.toString());
                                    if (TextUtils.isEmpty(input.toString())) {
                                        Toasty.warning(getActivity(), "必须填写机器号", Toast.LENGTH_SHORT, true).show();
                                        return;
                                    }
                                    SpUtils.put(getActivity(), AppConstant.Receipt.NO, input.toString());
                                })
                        .show();
                break;
            case R.id.rl_mac:
                break;
//            case R.id.active:
//                mPresenter.getToken();
//                break;
            case R.id.switch_print:
                break;
            case R.id.tv_head:
                new MaterialDialog.Builder(getActivity())
                        .title("修改抬头名称")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .positiveText("确定")
                        .positiveColorRes(R.color.colorPrimaryText)
                        .negativeText("取消")
                        .negativeColorRes(R.color.colorPrimaryText)
                        .input(
                                "",
                                tvHead.getText().toString().trim(),
                                false,
                                (dialog, input) -> {
                                    tvHead.setText(input.toString());
                                    SpUtils.put(getActivity(), AppConstant.Receipt.NAME, input.toString());
                                })

                        .show();
                break;
            case R.id.tv_address:
                new MaterialDialog.Builder(getActivity())
                        .title("修改地址名称")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .positiveText("确定")
                        .positiveColorRes(R.color.colorPrimaryText)
                        .negativeText("取消")
                        .negativeColorRes(R.color.colorPrimaryText)
                        .input(
                                "",
                                tvAddress.getText().toString().trim(),
                                false,
                                (dialog, input) -> {
                                    tvAddress.setText(input.toString());
                                    SpUtils.put(getActivity(), AppConstant.Receipt.ADDRESS, input.toString());
                                })

                        .show();
                break;
            case R.id.tv_phone:
                new MaterialDialog.Builder(getActivity())
                        .title("修改联系电话")
                        .inputType(InputType.TYPE_CLASS_PHONE)
                        .positiveText("确定")
                        .positiveColorRes(R.color.colorPrimaryText)
                        .negativeText("取消")
                        .negativeColorRes(R.color.colorPrimaryText)
                        .input(
                                "",
                                tvPhone.getText().toString().trim(),
                                false,
                                (dialog, input) -> {
                                    tvPhone.setText(input.toString());
                                    SpUtils.put(getActivity(), AppConstant.Receipt.PHONE, input.toString());
                                })
                        .show();
                break;
            case R.id.bar:
                break;
            case R.id.cache:
                DataCleanManager.cleanCustomCache(Environment.getExternalStorageDirectory() + "/" + "ScreenShots");
                try {
                    if (DataCleanManager.getTotalCacheSize(getActivity()).startsWith("0")) {
                        showMessage("清理完成");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    cache.setText("清除缓存");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.version:
                TaskParams params = new TaskParams();
                MainTask.UpdateTask dbTask = new MainTask.UpdateTask(getActivity());
                dbTask.execute(params);
                break;
            case R.id.exit:
                AboutDialog.newInstance().show(getFragmentManager(), "TAG");
                break;
//            case R.id.compress:
//                String max_size = (String) SpUtils.get(getActivity(), AppConstant.Api.LIMIT_MAX_SIZE, "");
//                if (!TextUtils.isEmpty(max_size)) {
//                    pic_max_size = Integer.valueOf(max_size);
//                    for (int i = 0; i < items.length; i++) {
//                        if (max_size.equals(items[i])){
//                            which_one = i;
//                            break;
//                        }
//                    }
//                    Log.e(TAG, "max_size: " + pic_max_size+" which_one: "+which_one);
//                }
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), 0);
//                builder.setTitle("单选");
//                builder.setIcon(android.R.drawable.ic_dialog_info);
//                builder.setSingleChoiceItems(items, which_one,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // 选中后实现的操作
//                                dialog.dismiss();
//                                tv_max_size.setText(items[which]);
//                                Toast.makeText(getActivity(), items[which], Toast.LENGTH_SHORT).show();
//                                SpUtils.put(getActivity(), AppConstant.Api.LIMIT_MAX_SIZE, items[which]);
//                            }
//                        });
//
//                builder.create().show();
//
//
//                break;
        }
        }
}
