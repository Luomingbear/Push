package com.bearever.push.handle.impl;

import android.content.Context;
import android.util.Log;

import com.bearever.push.model.ReceiverInfo;

/**
 * 处理用户点击通知栏的操作
 * Created by luoming on 2018/5/28.
 */

public class HandleReceiverNotificationOpened implements BaseHandleListener {
    private static final String TAG = "HandleReceiverNotificat";

    @Override
    public void handle(Context context, ReceiverInfo info) {
        Log.d(TAG, "handle: " + info.getContent());
    }
}
