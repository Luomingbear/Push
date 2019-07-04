package com.bearever.push.target.huawei;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bearever.push.receiver.PushReceiverHandleManager;
import com.bearever.push.model.PushTargetEnum;
import com.bearever.push.model.ReceiverInfo;

/**
 * 处理华为推送点击事件
 * 需要与后端协商好数据结构
 * scheme ：ipush://router/huawei
 * 作者：luoming on 2018/10/13.
 * 邮箱：luomingbear@163.com
 * 版本：v1.0
 */
public class HuaweiLoadActivity extends AppCompatActivity {
    private static final String TAG = "HuaweiLoadActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri uri = getIntent().getData();
        String data = "";
        // TODO: 2018/10/15 与后端协商好数据结构，这里的“data”是与后端协商的key
        if (uri != null) {
            data = uri.getQueryParameter("data");
        }
        ReceiverInfo openInfo = new ReceiverInfo();
        openInfo.setPushTarget(PushTargetEnum.HUAWEI);
        openInfo.setExtra(data);
        PushReceiverHandleManager.getInstance().onNotificationOpened(this, openInfo);
        finish();
    }
}
