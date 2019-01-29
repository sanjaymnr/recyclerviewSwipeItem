package com.saran.recyclerswipeanimate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by core I5 on 5/10/2017.
 */

public class SecondActivity extends AppCompatActivity {

    RecyclerView rv;
    List<String> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        rv = (RecyclerView) findViewById(R.id.rv_main);
        MyRecyclerViewSwipe obj = new MyRecyclerViewSwipe(this, rv, list);
        obj.setRecyclerView();
    }

    private void init() {
        list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list.add("Btn " + i);
        }
    }

}
