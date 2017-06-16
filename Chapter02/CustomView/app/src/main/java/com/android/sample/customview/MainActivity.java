package com.android.sample.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private StarIndicatorView mStarIndicatorView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStarIndicatorView = (StarIndicatorView) findViewById(R.id.star_indicator_view);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = mStarIndicatorView.getSelected();
                if (selected == 2) {
                    selected = 0;
                } else {
                    selected++;
                }
                mStarIndicatorView.setSelected(selected);
            }
        });


    }
}
