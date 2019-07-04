package com.bearever.push.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bearever.push.model.ReceiverInfo;
import com.google.gson.Gson;

/**
 * 推送集成聚合广播接收器
 *
 * @author :  malong    luomingbear@163.com
 * @date :  2019/4/18
 **/
public abstract class BasePushBroadcastReceiver extends BroadcastReceiver {
    public BasePushBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }

        String json = intent.getStringExtra("receiverinfo");
        ReceiverInfo info = new Gson().fromJson(json, ReceiverInfo.class);
//        根据推送平台确定原始数据的类型
//        PushTargetEnum pushTargetEnum = (PushTargetEnum) intent.getSerializableExtra("pushTarget");
//        switch (pushTargetEnum) {
//            case MEIZU:
//
//                break;
//            case HUAWEI:
//                break;
//            case JPUSH:
//                break;
//            case OPPO:
//                break;
//            case VIVO:
//                break;
//            case XIAOMI:
//                break;
//        }
        int type = intent.getIntExtra("type", 0);
        switch (type) {
            case PushReceiverHandleManager.TYPE_REGISTRATION:
                onRegister(context, info);
                break;
            case PushReceiverHandleManager.TYPE_ALIAS:
                onAlias(context, info);
                break;
            case PushReceiverHandleManager.TYPE_NOTIFICATION:
                onNotification(context, info);
                break;
            case PushReceiverHandleManager.TYPE_MESSAGE:
                onMessage(context, info);
                break;
            case PushReceiverHandleManager.TYPE_OPEN:
                onOpened(context, info);
                break;
            default:
                break;
        }
    }

    /**
     * 注册成功回调
     *
     * @param info info.getContent() 对应的就是具体的推送平台的注册id ，例如小米的regid和华为的TMID
     */
    public abstract void onRegister(Context context, ReceiverInfo info);

    /**
     * 别名设置完成回调
     *
     * @param info info.getContent() 对应的就是设置的别名
     */
    public abstract void onAlias(Context context, ReceiverInfo info);

    /**
     * 收到穿透消息的回调
     *
     * @param info info.getContent对应的是消息文本
     */
    public abstract void onMessage(Context context, ReceiverInfo info);

    /**
     * 收到通知的回调
     *
     * @param info info.getContent对应的是消息文本
     */
    public abstract void onNotification(Context context, ReceiverInfo info);

    /**
     * 点击通知的回调
     *
     * @param info info.getContent对应的是消息文本
     */
    public abstract void onOpened(Context context, ReceiverInfo info);
}
