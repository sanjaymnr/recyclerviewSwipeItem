package com.saran.recyclerswipeanimate;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by core I5 on 5/10/2017.
 */

public class OnSwipeTouchListener implements View.OnTouchListener {

    private final String TAG = OnSwipeTouchListener.class.getSimpleName();
    private static float distance = 0;
    private final LinearLayout mll;
    private static float delta_x = 0;
    private TouchItemMoveCallback mTouchItemMoveCallback;
    private MyAdapter.MyVH viewHolder;
    private boolean isMoved = false;
    private float original_x = 0;
    private static double fraction = 0;

    public OnSwipeTouchListener(MyAdapter.MyVH vh, TouchItemMoveCallback callback) {
        viewHolder = vh;
        mll = vh.ll;
        mTouchItemMoveCallback = callback;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int max_dist = (mll.getWidth() / 4);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
        int gravity = lp.gravity;

        if(!isMoved){
            original_x = view.getX();
        }
        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                float x1 = motionEvent.getRawX();
                if (gravity == Gravity.RIGHT) {
                    delta_x = x1 - lp.getMarginEnd();
                } else {
                    delta_x = x1 - lp.getMarginStart();
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                isMoved = true;
                mTouchItemMoveCallback.disableScroll(false);
                float x2 = motionEvent.getRawX();
                float margin = 0;
                if (gravity == Gravity.RIGHT) {
                    margin = x2 - delta_x;
                    margin = -margin;
                    if(margin<0){
                        distance = 0;
                        break;
                    }
                    lp.setMarginEnd((int) margin);
                    fraction = margin/(mll.getWidth()-original_x-view.getWidth());
                    if(fraction >= 1){
                        view.setBackgroundColor(Color.BLUE);
                    } else {
                        view.setBackgroundColor((int)new ArgbEvaluator().evaluate((float) fraction, Color.RED,Color.BLUE));
                    }
                    if(margin >= mll.getWidth()/2-view.getWidth()/2-view.getWidth()*0.1){
                        break;
                    }

                } else {
                    margin = x2 - delta_x;
                    lp.setMarginStart((int) margin);
                    fraction = margin/(mll.getWidth()-original_x-view.getWidth());
                    if(gravity == Gravity.CENTER_HORIZONTAL){
                        if(lp.getMarginStart() == 0) {
                            view.setBackgroundColor(Color.BLUE);
                        } else if(original_x<view.getX() && lp.getMarginStart()>0){
                            if(fraction >= 1){
                                view.setBackgroundColor(Color.RED);
                            } else {
                                view.setBackgroundColor((int)new ArgbEvaluator().evaluate((float) fraction, Color.BLUE,Color.RED));
                            }
                            if(margin >= mll.getWidth() - original_x - view.getWidth()){
                                break;
                            }
                        } else if(original_x>view.getX() && lp.getMarginStart()<0) {
                            if(-fraction >= 1){
                                view.setBackgroundColor(Color.GREEN);
                            } else {
                                view.setBackgroundColor((int)new ArgbEvaluator().evaluate((float) -fraction, Color.BLUE,Color.GREEN));
                            }
                            if (-margin >= mll.getWidth() - original_x - view.getWidth()){
                                break;
                            }
                        }

                    } else if(gravity == Gravity.LEFT){
                        if(margin<0){
                            distance = 0;
                            break;
                        }
                        if(fraction >= 1){
                            view.setBackgroundColor(Color.BLUE);
                        } else {
                            view.setBackgroundColor((int)new ArgbEvaluator().evaluate((float) fraction, Color.GREEN,Color.BLUE));
                        }
                        if(margin >= mll.getWidth()/2-view.getWidth()/2-view.getWidth()*0.1){
                            break;
                        }
                    }
                }
                distance = margin;
                if(gravity == Gravity.CENTER_HORIZONTAL){
                    mTouchItemMoveCallback.onDistanceChanged(distance,original_x,view.getX());
                }
                view.setLayoutParams(lp);

                break;
            }
            case MotionEvent.ACTION_UP: {
                mTouchItemMoveCallback.disableScroll(true);
                Log.d(TAG, "ISMOVED " + isMoved);
                if (isMoved) {
                    Log.d(TAG, "ACTION UP");
                    if (gravity == Gravity.CENTER_HORIZONTAL) {
                        if (distance > (max_dist-view.getWidth()/2)) {
                            lp.gravity = Gravity.RIGHT;
                            lp.setMarginStart(0);
                            view.setLayoutParams(lp);
                            view.setBackgroundColor(Color.RED);
                            mTouchItemMoveCallback.onItemMove(viewHolder.getAdapterPosition(), Gravity.RIGHT, gravity);
                        } else if (distance < (-(max_dist - view.getWidth()/2))) {
                            lp.gravity = Gravity.LEFT;
                            lp.setMarginStart(0);
                            view.setLayoutParams(lp);
                            view.setBackgroundColor(Color.GREEN);
                            mTouchItemMoveCallback.onItemMove(viewHolder.getAdapterPosition(), Gravity.LEFT, gravity);
                        } else {
                            lp.setMarginStart(0);
                            view.setLayoutParams(lp);
                            view.setBackgroundColor(Color.BLUE);
                            mTouchItemMoveCallback.resetMargins();
                        }
                    } else if (gravity == Gravity.RIGHT) {
                        if (distance > (max_dist - view.getWidth()/2)) {
                            lp.gravity = Gravity.CENTER_HORIZONTAL;
                            lp.setMarginEnd(0);
                            view.setLayoutParams(lp);
                            mTouchItemMoveCallback.onItemMove(viewHolder.getAdapterPosition(), Gravity.CENTER_HORIZONTAL, gravity);
                        } else {
                            lp.setMarginEnd(0);
                            view.setLayoutParams(lp);
                            view.setBackgroundColor(Color.RED);
                            mTouchItemMoveCallback.resetMargins();
                        }
                    } else if (gravity == Gravity.LEFT) {
                        if (distance > (max_dist - view.getWidth()/2)) {
                            lp.gravity = Gravity.CENTER_HORIZONTAL;
                            lp.setMarginStart(0);
                            view.setLayoutParams(lp);
                            mTouchItemMoveCallback.onItemMove(viewHolder.getAdapterPosition(), Gravity.CENTER_HORIZONTAL, gravity);
                        } else {
                            lp.setMarginStart(0);
                            view.setLayoutParams(lp);
                            view.setBackgroundColor(Color.GREEN);
                            mTouchItemMoveCallback.resetMargins();
                        }
                    }
                }

                break;
            }
            default: {
                lp.setMargins(0, 0, 0, 0);
                view.setLayoutParams(lp);
                break;
            }
        }

        return true;
    }
}
