package com.lianxi.dingtu.dingtu_urovo.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lianxi.dingtu.dingtu_urovo.R;
import com.lianxi.dingtu.dingtu_urovo.app.entity.EMGoodsTypeTo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description ：NetSettingRvAdapter
 * author : scy
 * email : 1797484636@qq.com
 * date : 2019/7/29 08:46
 */
public class EMGoodsRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    List<EMGoodsTypeTo> rowsBeans;
    private OnMyItemClickListener myItemClickListener;

    public interface OnMyItemClickListener {
        void onItemClick(String id, String name, int positon);
    }

    public void setMyItemClickListener(OnMyItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }

    public EMGoodsRvAdapter(Context context, List<EMGoodsTypeTo> rowsBeans) {
        this.context = context;
        this.rowsBeans = rowsBeans;
    }

    private int selectPosition;

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv1)
        TextView tv1;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (myItemClickListener != null) {
                        myItemClickListener.onItemClick(rowsBeans.get(getAdapterPosition()).getId(), rowsBeans.get(getAdapterPosition()).getName(), getAdapterPosition());
                    }
                }
            });


        }

        public void bind() {
            tv1.setText(rowsBeans.get(getAdapterPosition()).getName());
            if (getAdapterPosition() == getSelectPosition()) {
                tv1.setBackgroundColor(context.getResources().getColor(R.color.bk_blue));
                tv1.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                tv1.setBackgroundColor(context.getResources().getColor(R.color.white));
                tv1.setTextColor(context.getResources().getColor(R.color.black));
            }
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.emgood_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).bind();
    }

    @Override
    public int getItemCount() {
        return rowsBeans != null ? rowsBeans.size() : 0;
    }

}
