package com.saran.recyclerswipeanimate;

import android.support.v7.widget.RecyclerView;

/**
 * Created by core I5 on 5/11/2017.
 */

public interface TouchItemMoveCallback {
    void onItemMove(int position, int new_gravity, int prev_gravity);
    void disableScroll(boolean flag);
    void onDistanceChanged(float distance, float prev_x, float new_x);
    void resetMargins();
}
