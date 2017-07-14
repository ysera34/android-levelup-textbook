package com.android.sample.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by yoon on 2017. 7. 14..
 */

public class SimpleRecyclerViewActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mPositionEditText;
    private EditText mTextEditText;

    private RecyclerView mSimpleRecyclerView;
    private SimpleStringAdapter mSimpleStringAdapter;
    private ManipulationSimpleStringAdapter mManipulationSimpleStringAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_recycler_view);

        mPositionEditText = (EditText) findViewById(R.id.position_edit_Text);
        mTextEditText = (EditText) findViewById(R.id.text_edit_text);
        findViewById(R.id.add_button).setOnClickListener(this);
        findViewById(R.id.remove_button).setOnClickListener(this);
        setupRecyclerView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_button:
                mManipulationSimpleStringAdapter.addAtPosition(
                        Integer.valueOf(mPositionEditText.getText().toString()), mTextEditText.getText().toString());
                break;
            case R.id.remove_button:
                mManipulationSimpleStringAdapter.removeAtPosition(
                        Integer.valueOf(mPositionEditText.getText().toString()));
                break;
        }
    }

    private void setupRecyclerView() {

        mSimpleRecyclerView = (RecyclerView) findViewById(R.id.simple_recycler_view);
        mSimpleRecyclerView.setHasFixedSize(true);

//        mSimpleStringAdapter = new SimpleStringAdapter(generateStringListData());
        mManipulationSimpleStringAdapter = new ManipulationSimpleStringAdapter(generateStringListData());
        mManipulationSimpleStringAdapter.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Position: " + mSimpleRecyclerView.getChildAdapterPosition(v) + " clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });
//        mSimpleRecyclerView.setAdapter(mSimpleStringAdapter);
        mSimpleRecyclerView.setAdapter(mManipulationSimpleStringAdapter);
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
