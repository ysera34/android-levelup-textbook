package com.android.sample.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements MainFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onFragmentInteraction() {
        Toast.makeText(getApplicationContext(), "button clicked", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }
}
