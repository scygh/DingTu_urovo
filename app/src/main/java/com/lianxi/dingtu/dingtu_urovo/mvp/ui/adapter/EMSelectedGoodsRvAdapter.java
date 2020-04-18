package com.lianxi.dingtu.dingtu_urovo.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianxi.dingtu.dingtu_urovo.R;
import com.lianxi.dingtu.dingtu_urovo.app.entity.EMGoodsTo2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description ：NetSettingRvAdapter
 * author : scy
 * email : 1797484636@qq.com
 * date : 2019/7/29 08:46
 */
public class EMSelectedGoodsRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    List<List<EMGoodsTo2>> rowsBeans;
    private OnMyItemClickListener myItemClickListener;

    public interface OnMyItemClickListener {
        void onAddClick(int position, int count);

        void onReduceClick(int position, int count);
    }

    public void setMyItemClickListener(OnMyItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }

    public EMSelectedGoodsRvAdapter(Context context, List<List<EMGoodsTo2>> rowsBeans) {
        this.context = context;
        this.rowsBeans = rowsBeans;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_name)
        TextView goodsName;
        @BindView(R.id.goods_price)
        TextView goodsPrice;
        @BindView(R.id.goods_count)
        TextView goodsCount;
        @BindView(R.id.add_goods)
        ImageView ivAdd;
        @BindView(R.id.iv_reduce)
        ImageView ivReduce;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setItem(int position) {
            List<EMGoodsTo2> list = rowsBeans.get(position);
            goodsName.setText(list.get(0).getGoods().getGoodsName());
            goodsPrice.setText(list.get(0).getGoods().getPrice() + "");
            goodsCount.setText(list.size()+"");
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.selected_goods_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder myholder = (ItemViewHolder) holder;
        myholder.setItem(position);
        myholder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myItemClickListener != null) {
                    int count = rowsBeans.get(position).size() + 1;
                    myItemClickListener.onAddClick(position, count);
                }
            }
        });
        myholder.ivReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myItemClickListener != null) {
                    int count = rowsBeans.get(position).size();
                    if (count > 0) {
                        count = count - 1;
                        myItemClickListener.onReduceClick(position, count);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rowsBeans.size();
    }

}
