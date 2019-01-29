package com.saran.recyclerswipeanimate;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by core I5 on 5/11/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyVH> implements TouchItemMoveCallback {

    private Context mCntx;
    private List<String> mList;
    private MyLinearLayoutManager mLLM;
    private List<Integer> garvityList;
    private int left_position = -1;
    private int right_position = -1;
    private ViewItemMoveCallback mViewItemMoveCallback;
    private boolean leftChanged = false;
    private boolean rightChanged = false;

    public MyAdapter(Context context, List<String> list, List<Integer> gList, MyLinearLayoutManager llm, ViewItemMoveCallback vimc){
        mCntx = context;
        mList = list;
        garvityList = gList;
        mLLM = llm;
        mViewItemMoveCallback = vimc;
    }

    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCntx).inflate(R.layout.test_layout,parent,false);
        return new MyVH(view);
    }

    @Override
    public void onBindViewHolder(final MyVH holder, int position) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.btn.getLayoutParams();
        if(garvityList.get(position) == Gravity.CENTER_HORIZONTAL){
            lp.gravity = Gravity.CENTER_HORIZONTAL;
            holder.btn.setBackgroundColor(Color.BLUE);
        } else if(garvityList.get(position) == Gravity.RIGHT){
            lp.gravity = Gravity.RIGHT;
            holder.btn.setBackgroundColor(Color.RED);
        } else {
            lp.gravity = Gravity.LEFT;
            holder.btn.setBackgroundColor(Color.GREEN);
        }
        holder.btn.setLayoutParams(lp);
        holder.btn.setText(mList.get(position));
        holder.btn.setOnTouchListener(new OnSwipeTouchListener(holder,this));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onItemMove(int position, int new_gravity, int prev_gravity) {
        Log.d("TAG","Position " + position);
        if(new_gravity == Gravity.LEFT){
            if(left_position == -1){
                left_position = position;
            } else {
                garvityList.set(left_position,Gravity.CENTER_HORIZONTAL);
                mViewItemMoveCallback.resetLeftAfterChanged();
                notifyItemChanged(left_position);
                left_position = position;
            }
        } else if(new_gravity == Gravity.RIGHT){
            if(right_position == -1){
                right_position = position;
            } else {
                garvityList.set(right_position,Gravity.CENTER_HORIZONTAL);
                mViewItemMoveCallback.resetRightAfterChanged();
                notifyItemChanged(right_position);
                right_position = position;
            }
        } else if(new_gravity == Gravity.CENTER_HORIZONTAL){
            if(prev_gravity == Gravity.LEFT){
                if(left_position != -1){
                    notifyItemChanged(left_position);
                    left_position = -1;
                }
            } else if(prev_gravity == Gravity.RIGHT){
                if(right_position != -1){
                    notifyItemChanged(right_position);
                    right_position = -1;
                }
            }
        }
        garvityList.set(position,new_gravity);
    }

    @Override
    public void disableScroll(boolean flag) {
        mLLM.setScrollEnabled(flag);
    }

    @Override
    public void onDistanceChanged(float distance, float prev_x, float new_x) {
        if(left_position != -1 && (prev_x>new_x)){
            leftChanged = true;
            mViewItemMoveCallback.onItemMoveLeft(distance,prev_x);
            if(rightChanged){
                mViewItemMoveCallback.resetRightMargins();
                rightChanged = false;
            }
        } else if(right_position != -1 && (prev_x<new_x)){
            rightChanged = true;
            mViewItemMoveCallback.onItemMoveRight(distance,prev_x);
            if(leftChanged){
                mViewItemMoveCallback.resetLeftMargins();
                leftChanged = false;
            }
        } else if(right_position != -1 && left_position != -1 && prev_x==new_x){
            mViewItemMoveCallback.resetLeftMargins();
            mViewItemMoveCallback.resetRightMargins();
            leftChanged = false;
            rightChanged = false;
        }
    }

    @Override
    public void resetMargins() {
        if(leftChanged && rightChanged){
            mViewItemMoveCallback.resetLeftMargins();
            mViewItemMoveCallback.resetRightMargins();
            leftChanged = false;
            rightChanged = false;
        } else {
            if(leftChanged){
                mViewItemMoveCallback.resetLeftMargins();
                leftChanged = false;
            } else if(rightChanged){
                mViewItemMoveCallback.resetRightMargins();
                rightChanged = false;
            }
        }
    }


    public int getLeft_position() {
        return left_position;
    }

    public int getRight_position() {
        return right_position;
    }

    public class MyVH extends RecyclerView.ViewHolder {
        Button btn;
        LinearLayout ll;
        public MyVH(View itemView) {
            super(itemView);
            btn = (Button)itemView.findViewById(R.id.btn_test);
            ll = (LinearLayout)itemView.findViewById(R.id.llm);
        }
    }
}
