package com.android.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yoon on 2017. 6. 16..
 */

public class ParentFragment extends Fragment {

    private static final String TAG = ParentFragment.class.getSimpleName();
    private static final String TAG_CHILD = "tag_child";
    private static final String ARG_NUMBER = "number";

    private FragmentManager mChildFragmentManager;
    private FragmentManager.OnBackStackChangedListener mBackStackChangedListener;
    private int mNumber;

    public static ParentFragment newInstance() {

        Bundle args = new Bundle();

        ParentFragment fragment = new ParentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ParentFragment newInstance(int number) {

        Bundle args = new Bundle();
        args.putInt(ARG_NUMBER, number);

        ParentFragment fragment = new ParentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChildFragmentManager = getChildFragmentManager();
        mBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int count = 0;
                if (mChildFragmentManager.getFragments() != null) {
                    for (Fragment fragment : mChildFragmentManager.getFragments()) {
                        if (fragment != null) {
                            count++;
                        }
                    }
                    mNumber = count;
                    Log.d(TAG, "onBackStackChanged mNumber: " + mNumber);
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_parent, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChildFragmentManager.beginTransaction()
                        .add(R.id.fragment_container, ChildFragment.newInstance(mNumber))
                        .addToBackStack(null)
                        .commit();
            }
        });
        view.findViewById(R.id.remove_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNumber == 0) {
                    return;
                }
                mChildFragmentManager.popBackStack();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        if (savedInstanceState != null) {
            mNumber = savedInstanceState.getInt(ARG_NUMBER, 0);
        }
        Fragment fragment = mChildFragmentManager.findFragmentByTag(TAG_CHILD);
        Log.d(TAG, "onActivityCreated childFragmentManager: " + fragment + ", mNumber: " + mNumber);
        if (savedInstanceState == null) {
            mChildFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, ChildFragment.newInstance(mNumber), TAG_CHILD)
                    .addToBackStack(null)
                    .commit();
        }
        mChildFragmentManager.addOnBackStackChangedListener(mBackStackChangedListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mChildFragmentManager.removeOnBackStackChangedListener(mBackStackChangedListener);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: mNumber: " + mNumber);
        outState.putInt(ARG_NUMBER, mNumber);
    }
}
