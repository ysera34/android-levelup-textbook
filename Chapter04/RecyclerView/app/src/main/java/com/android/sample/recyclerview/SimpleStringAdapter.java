package com.android.sample.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yoon on 2017. 7. 14..
 */
public class SimpleStringAdapter extends RecyclerView.Adapter<SimpleStringAdapter.ViewHolder> {

    protected ArrayList<String> mStrings;
    private View.OnClickListener mOnItemViewClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.simple_text_view);
        }
    }

    public SimpleStringAdapter(ArrayList<String> strings) {
        mStrings = strings;
    }

    public void setOnItemViewClickListener(View.OnClickListener onItemViewClickListener) {
        mOnItemViewClickListener = onItemViewClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_row, parent, false);
        if (mOnItemViewClickListener != null) {
            view.setOnClickListener(mOnItemViewClickListener);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = mStrings.get(position);
        holder.mTextView.setText(String.valueOf(text));
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }
}
