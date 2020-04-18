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
public class EMDetailGoodsRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    List<EMGoodsTo2> rowsBeans;
    private OnMyItemClickListener myItemClickListener;

    public interface OnMyItemClickListener {
        void onAddClick(int position, int count);

        void onReduceClick(int position, int count);
    }

    public void setMyItemClickListener(OnMyItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }

    public EMDetailGoodsRvAdapter(Context context, List<EMGoodsTo2> rowsBeans) {
        this.context = context;
        this.rowsBeans = rowsBeans;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_name)
        TextView goodsName;
        @BindView(R.id.goods_price)
        TextView goodsPrice;
        @BindView(R.id.iv_reduce)
        ImageView ivReduce;
        @BindView(R.id.goods_count)
        TextView goodsCount;
        @BindView(R.id.add_goods)
        ImageView addGoods;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setItem(int position) {
            EMGoodsTo2 emGoodsTo2 = rowsBeans.get(position);
            goodsName.setText(emGoodsTo2.getGoods().getGoodsName());
            goodsPrice.setText(emGoodsTo2.getGoods().getPrice() + "");
            if (rowsBeans.get(position).getImgCount()==0) {
                ivReduce.setVisibility(View.GONE);
                goodsCount.setVisibility(View.GONE);
            } else {
                ivReduce.setVisibility(View.VISIBLE);
                goodsCount.setText(emGoodsTo2.getImgCount() + "");
                goodsCount.setVisibility(View.VISIBLE);
            }
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_goods_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder myholder = (ItemViewHolder) holder;
        myholder.setItem(position);
        myholder.addGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myItemClickListener != null) {
                    int count = rowsBeans.get(position).getImgCount() + 1;
                    rowsBeans.get(position).setImgCount(count);
                    notifyItemChanged(position);
                    myItemClickListener.onAddClick(position, count);
                }
            }
        });
        myholder.ivReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myItemClickListener != null) {
                    int count = rowsBeans.get(position).getImgCount();
                    if (count == 0) {

                    } else if (count > 0) {
                        count = count-1;
                        rowsBeans.get(position).setImgCount(count);
                        notifyItemChanged(position);
                        myItemClickListener.onReduceClick(position, count);
                    }
                    }
                }
            });
        }

        @Override
        public int getItemCount () {
            return rowsBeans.size();
        }

    }
