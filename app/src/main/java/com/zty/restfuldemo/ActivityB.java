package com.zty.restfuldemo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        Intent intent=new Intent();
        intent.putExtra(ActivityA.PARAM_NAME,23333);
        setResult(RESULT_OK,intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}