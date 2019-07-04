package com.bearever.push.target.jiguang;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.bearever.push.receiver.PushReceiverHandleManager;
import com.bearever.push.model.PushTargetEnum;
import com.bearever.push.model.ReceiverInfo;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义的极光推送接收
 * Created by luoming on 2018/5/28.
 */

public class JPushBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }

        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        }

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(action)) {
            //用户注册SDK的intent
            ReceiverInfo info = new ReceiverInfo();
            info.setPushTarget(PushTargetEnum.JPUSH);
            info.setRawData(intent);
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String code = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID, "");
                info.setContent(code);
            }
            info.setTitle("极光推送注册成功");
            PushReceiverHandleManager.getInstance().onRegistration(context, info);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(action)) {
            //用户接收SDK消息的intent
            PushReceiverHandleManager.getInstance().onMessageReceived(context, convert2MessageReceiverInfo(intent));
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(action)) {
            //用户接收SDK通知栏信息的intent
            PushReceiverHandleManager.getInstance().onNotificationReceived(context, convert2NotificationReceiverInfo(intent));
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(action)) {
            //用户打开自定义通知栏的intent
            PushReceiverHandleManager.getInstance().onNotificationOpened(context, convert2NotificationReceiverInfo(intent));
        }
    }

    /**
     * 将intent的数据转化为ReceiverInfo用于处理
     *
     * @param intent
     * @return
     */
    private ReceiverInfo convert2NotificationReceiverInfo(Intent intent) {
        ReceiverInfo info = new ReceiverInfo();
        Bundle bundle = intent.getExtras();
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

        info.setTitle(title);
        info.setContent(message);
        info.setExtra(extras);
        info.setPushTarget(PushTargetEnum.JPUSH);
        info.setRawData(intent);
        return info;
    }

    /**
     * 将intent的数据转化为ReceiverInfo用于处理
     *
     * @param intent
     * @return
     */
    private ReceiverInfo convert2MessageReceiverInfo(Intent intent) {
        ReceiverInfo info = new ReceiverInfo();
        Bundle bundle = intent.getExtras();
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

        info.setTitle(title);
        info.setContent(message);
        info.setExtra(extras);
        info.setPushTarget(PushTargetEnum.JPUSH);
        info.setRawData(intent);
        return info;
    }
}
