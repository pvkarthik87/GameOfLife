package com.kar.gameoflife.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kar.gameoflife.R;
import com.kar.gameoflife.controllers.LifeController;

/**
 * Created by Karthik on 5/31/2016.
 */
public class LifeAdapter extends RecyclerView.Adapter<LifeAdapter.LifeViewHolder> {

    private LifeController mLifeController;

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (Integer) v.getTag();
            toggleState(position);
        }
    };

    public LifeAdapter(LifeController controller) {
        mLifeController = controller;
    }

    @Override
    public int getItemCount() {
        return mLifeController.getCount();
    }

    @Override
    public LifeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LifeViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_life_item, parent, false));
    }

    @Override
    public void onBindViewHolder(LifeViewHolder holder, int position) {
        if(mLifeController.isAlive(position)) {
            holder.mLifeImgView.setBackgroundResource(R.drawable.life_alive);
        }
        else {
            holder.mLifeImgView.setBackgroundResource(R.drawable.life_dead);
        }
        holder.mLifeImgView.setTag(position);
        holder.mLifeImgView.setOnClickListener(mClickListener);
    }

    private void toggleState(int position) {
        mLifeController.toggleState(position);
        notifyItemChanged(position);
    }

    public static class LifeViewHolder extends RecyclerView.ViewHolder {

        private ImageView mLifeImgView;

        public LifeViewHolder(View itemView) {
            super(itemView);
            mLifeImgView = (ImageView) itemView.findViewById(R.id.life_item);
        }

    }
}
