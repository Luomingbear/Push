package com.bearever.push.target.xiaomi;

import android.content.Context;
import android.util.Log;

import com.bearever.push.model.PushTargetEnum;
import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.receiver.PushReceiverHandleManager;
import com.google.gson.Gson;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

/**
 * 自定义的小米推送接收
 * Created by luoming on 2018/5/28.
 */

public class XiaomiBroadcastReceiver extends PushMessageReceiver {
    @Override
    public void onReceivePassThroughMessage(Context var1, MiPushMessage var2) {
        PushReceiverHandleManager.getInstance().onMessageReceived(var1, convert2ReceiverInfo(var2));
    }

    @Override
    public void onNotificationMessageClicked(Context var1, MiPushMessage var2) {
        PushReceiverHandleManager.getInstance().onNotificationOpened(var1, convert2ReceiverInfo(var2));
    }

    @Override
    public void onNotificationMessageArrived(Context var1, MiPushMessage var2) {
        Log.e("接收消息成功", "------------------------");
        PushReceiverHandleManager.getInstance().onNotificationReceived(var1, convert2ReceiverInfo(var2));
    }

    @Override
    public void onReceiveRegisterResult(Context var1, MiPushCommandMessage command) {
        if (MiPushClient.COMMAND_REGISTER.equals(command.getCommand())) {
            ReceiverInfo info = convert2ReceiverInfo(command);
            info.setTitle("小米推送注册成功");
            //注册id
            String id = command.getCommandArguments().get(0);
            info.setContent(id);
            Log.e("command id", id);
            PushReceiverHandleManager.getInstance().onRegistration(var1, info);
        }
    }

    /**
     * 将intent的数据转化为ReceiverInfo用于处理
     *
     * @param miPushMessage
     * @return
     */
    private ReceiverInfo convert2ReceiverInfo(MiPushMessage miPushMessage) {
        ReceiverInfo info = new ReceiverInfo();
        info.setContent(miPushMessage.getContent());
        info.setPushTarget(PushTargetEnum.XIAOMI);
        info.setTitle(miPushMessage.getTitle());
        info.setRawData(miPushMessage);
        if (miPushMessage.getExtra() != null) {
            info.setExtra(new Gson().toJson(miPushMessage.getExtra()));
        }
        return info;
    }

    /**
     * 将intent的数据转化为ReceiverInfo用于处理
     *
     * @param miPushCommandMessage
     * @return
     */
    private ReceiverInfo convert2ReceiverInfo(MiPushCommandMessage miPushCommandMessage) {
        ReceiverInfo info = new ReceiverInfo();
        info.setContent(miPushCommandMessage.getCommand());
        info.setRawData(miPushCommandMessage);
        info.setPushTarget(PushTargetEnum.XIAOMI);
        return info;
    }
}
