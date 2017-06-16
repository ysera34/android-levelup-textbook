package com.android.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yoon on 2017. 6. 16..
 */

public class ChildFragment extends Fragment {

    private static final String ARG_NUMBER = "number";

    public static ChildFragment newInstance() {

        Bundle args = new Bundle();

        ChildFragment fragment = new ChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ChildFragment newInstance(int number) {

        Bundle args = new Bundle();
        args.putInt(ARG_NUMBER, number);

        ChildFragment fragment = new ChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private int mNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_child, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) view.findViewById(R.id.text_view);
        mNumber = getArguments().getInt(ARG_NUMBER, 0);
        String text = "No." + mNumber + " ChildFragment";
        textView.setText(text);
    }
}
