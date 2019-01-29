package com.saran.recyclerswipeanimate;

/**
 * Created by core I5 on 5/12/2017.
 */

public interface ViewItemMoveCallback {
    void onItemMoveRight(float distance,float original_pos);
    void onItemMoveLeft(float distance, float original_pos);
    void resetLeftMargins();
    void resetRightMargins();
    void resetRightAfterChanged();
    void resetLeftAfterChanged();
}
