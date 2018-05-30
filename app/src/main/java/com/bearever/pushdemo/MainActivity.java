package com.bearever.pushdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bearever.push.target.huawei.HuaweiPushInitFromActivityUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HuaweiPushInitFromActivityUtil.init(this);
    }
}
