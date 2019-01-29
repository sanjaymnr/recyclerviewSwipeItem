package com.saran.recyclerswipeanimate;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by core I5 on 5/12/2017.
 */

public class MyRecyclerViewSwipe implements ViewItemMoveCallback {
    private RecyclerView mRecyclerView;
    private Context mContext;
    private List<String> mDataList;
    private List<Integer> mGravityList;
    private MyAdapter adapter;

    public MyRecyclerViewSwipe(Context context, RecyclerView recyclerView, List<String> dataList){
        mContext = context;
        mRecyclerView = recyclerView;
        mDataList = dataList;
        if(dataList!=null || dataList.size() > 0){
            setGravityList(dataList);
        }
    }

    private void setGravityList(List<String> list){
        mGravityList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            mGravityList.add(Gravity.CENTER_HORIZONTAL);
        }
    }

    public void setRecyclerView(){
        MyLinearLayoutManager llm = new MyLinearLayoutManager(mContext.getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        adapter = new MyAdapter(mContext,mDataList,mGravityList,llm,this);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemMoveRight(float distance,float orginal_pos) {
        MyAdapter.MyVH vh = (MyAdapter.MyVH)mRecyclerView.findViewHolderForAdapterPosition(adapter.getRight_position());
        if(vh!=null){
            Log.d("TAG", "OKRIGHT!!!!");
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) vh.btn.getLayoutParams();
            if(vh.btn.getX()<(vh.ll.getWidth()/2 - vh.btn.getWidth()/3)){
                vh.btn.setBackgroundColor(Color.BLUE);
            } else {
                double increase = distance/(vh.ll.getWidth()-vh.btn.getWidth()-orginal_pos);
                vh.btn.setBackgroundColor((int)new ArgbEvaluator().evaluate((float) increase, Color.RED,Color.BLUE));
            }
            lp.setMarginEnd((int)distance);
            vh.btn.setLayoutParams(lp);
        }
    }

    @Override
    public void onItemMoveLeft(float distance,float original_pos) {
        MyAdapter.MyVH vh = (MyAdapter.MyVH)mRecyclerView.findViewHolderForAdapterPosition(adapter.getLeft_position());
        if(vh!=null){
            Log.d("TAG", "OKLEFT!!!!");
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) vh.btn.getLayoutParams();
            if(vh.btn.getX()<(vh.ll.getWidth()/2 - vh.btn.getWidth()/3)){
                double increase = distance/(vh.ll.getWidth()-vh.btn.getWidth()-original_pos);
                vh.btn.setBackgroundColor((int)new ArgbEvaluator().evaluate((float) -increase, Color.GREEN,Color.BLUE));
            } else {
                vh.btn.setBackgroundColor(Color.BLUE);
            }

            lp.setMarginStart((int)-distance);
            vh.btn.setLayoutParams(lp);
        }
    }

    @Override
    public void resetLeftMargins() {
        MyAdapter.MyVH vh = (MyAdapter.MyVH)mRecyclerView.findViewHolderForAdapterPosition(adapter.getLeft_position());
        if(vh!=null){
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) vh.btn.getLayoutParams();
            vh.btn.setBackgroundColor(Color.GREEN);
            lp.setMarginStart(0);
            vh.btn.setLayoutParams(lp);
        }
    }

    @Override
    public void resetRightMargins() {
        MyAdapter.MyVH vh = (MyAdapter.MyVH)mRecyclerView.findViewHolderForAdapterPosition(adapter.getRight_position());
        if(vh!=null){
            Log.d("TAG", "OKRIGHT!!!!");
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) vh.btn.getLayoutParams();
            vh.btn.setBackgroundColor(Color.RED);
            lp.setMarginEnd(0);
            vh.btn.setLayoutParams(lp);
        }
    }

    @Override
    public void resetRightAfterChanged() {
        MyAdapter.MyVH vh = (MyAdapter.MyVH)mRecyclerView.findViewHolderForAdapterPosition(adapter.getRight_position());
        if(vh!=null){
            Log.d("TAG", "OKRIGHT!!!!");
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) vh.btn.getLayoutParams();
            lp.setMarginEnd(0);
            lp.gravity = Gravity.CENTER_HORIZONTAL;
            vh.btn.setLayoutParams(lp);
        }
    }

    @Override
    public void resetLeftAfterChanged() {
        MyAdapter.MyVH vh = (MyAdapter.MyVH)mRecyclerView.findViewHolderForAdapterPosition(adapter.getLeft_position());
        if(vh!=null){
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) vh.btn.getLayoutParams();
            lp.setMarginStart(0);
            lp.gravity = Gravity.CENTER_HORIZONTAL;
            vh.btn.setLayoutParams(lp);
        }
    }
}
