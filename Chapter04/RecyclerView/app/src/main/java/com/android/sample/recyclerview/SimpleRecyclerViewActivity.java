package com.android.sample.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by yoon on 2017. 7. 14..
 */

public class SimpleRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mSimpleRecyclerView;
    private SimpleStringAdapter mSimpleStringAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_recycler_view);
        setupRecyclerView();
    }

    private void setupRecyclerView() {

        mSimpleRecyclerView = (RecyclerView) findViewById(R.id.simple_recycler_view);
        mSimpleRecyclerView.setHasFixedSize(true);

        mSimpleStringAdapter = new SimpleStringAdapter(generateStringListData());
        mSimpleStringAdapter.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Position: " +mSimpleRecyclerView.getChildAdapterPosition(v) + " clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });
        mSimpleRecyclerView.setAdapter(mSimpleStringAdapter);
        mSimpleRecyclerView.addItemDecoration(new DividerItemDecorator(this));
    }

    private ArrayList<String> generateStringListData() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("java coding" + i);
            strings.add("coding pattern" + i);
            strings.add("start hadoop" + i);
            strings.add("python start" + i);
            strings.add("programming pattern" + i);
        }
        return strings;
    }
}
