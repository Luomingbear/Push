package com.bearever.push.handle.impl;

import android.content.Context;
import android.util.Log;

import com.bearever.push.handle.PushReceiverHandleManager;
import com.bearever.push.model.PushTarget;
import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.util.ApplicationUtil;
import com.xiaomi.mipush.sdk.MiPushClient;

import cn.jpush.android.api.JPushInterface;

/**
 * 处理用户注册SDK
 * 设置别名
 * Created by luoming on 2018/5/28.
 */

public class HandleReceiverRegistration implements BaseHandleListener {
    private static final String TAG = "HandleReceiverRegistrat";

    @Override
    public void handle(Context context, ReceiverInfo info) {
        Log.i(TAG, "handle: " + info.getContent());
    }
}
