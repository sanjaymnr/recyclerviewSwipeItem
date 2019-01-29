package com.saran.recyclerswipeanimate;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by core I5 on 5/11/2017.
 */

public class MyLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;
    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public void setScrollEnabled(boolean flag){
        isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled;
    }
}
