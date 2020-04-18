package com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.dingtu_urovo.R;
import com.lianxi.dingtu.dingtu_urovo.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_urovo.app.entity.DepositReportTo;
import com.lianxi.dingtu.dingtu_urovo.di.component.DaggerDepositComponent;
import com.lianxi.dingtu.dingtu_urovo.mvp.contract.DepositContract;
import com.lianxi.dingtu.dingtu_urovo.mvp.presenter.DepositPresenter;
import com.lianxi.dingtu.dingtu_urovo.mvp.ui.adapter.DepositAdapter;
;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 15:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class DepositActivity extends BaseActivity<DepositPresenter> implements DepositContract.View , SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private BaseQuickAdapter adapter;
    private List<DepositReportTo> data = new ArrayList<>();
    View footView;
    private View notDataView;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDepositComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_deposit; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("充值记录");
        mRecyclerView.setHasFixedSize(true);
        adapter = new DepositAdapter(R.layout.item_list_de, data);
        adapter.openLoadAnimation(view -> new Animator[]{
                ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
                ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
        });
        adapter.setOnLoadMoreListener(() -> mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.setList(false);
            }
        }, 500), mRecyclerView);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefresh.setProgressViewOffset(true, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, getResources()
                        .getDisplayMetrics()));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, 8);
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra(AppConstant.ActivityIntent.MODEL_DEPOSIT, data.get(position));
                intent.setClass(DepositActivity.this, ChooseActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        onRefresh();
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
    }

    @Override
    public void showLoading() {
        if (!mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
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
        finish();
    }

    @Override public void setData(List<DepositReportTo> list, boolean isPullRefresh) {
        if (isPullRefresh) {
            data.clear();
            adapter.removeFooterView(footView);
            adapter.setEnableLoadMore(true);
            data.addAll(list);
            adapter.setNewData(list);

        } else {
            adapter.loadMoreComplete();
            if (list.size() < 1) {
                adapter.loadMoreEnd();
            } else {
                data.addAll(list);
                adapter.addData(list);
            }
        }
    }

    @Override public void isErr() {
        adapter.loadMoreFail();
    }

    @Override public void noData() {
        adapter.setEmptyView(notDataView);
    }

    @Override public void onRefresh() {
        mPresenter.setList(true);
    }
}
