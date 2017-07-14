package com.android.sample.recyclerview;

import java.util.ArrayList;

/**
 * Created by yoon on 2017. 7. 14..
 */

public class ManipulationSimpleStringAdapter extends SimpleStringAdapter {

    public ManipulationSimpleStringAdapter(ArrayList<String> strings) {
        super(strings);
    }

    public void addAtPosition(int position, String text) {
        if (position > mStrings.size()) {
            position = mStrings.size();
        }
        mStrings.add(position, text);
        notifyItemInserted(position);
    }

    public void removeAtPosition(int position) {
        if (position < mStrings.size()) {
            mStrings.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void move(int fromPosition, int toPosition) {
        final String text = mStrings.get(fromPosition);
        mStrings.remove(fromPosition);
        mStrings.add(toPosition, text);
        notifyItemMoved(fromPosition, toPosition);
    }
}
