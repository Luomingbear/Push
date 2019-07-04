package com.bearever.push.target.vivo;

import android.content.Context;

import com.bearever.push.receiver.PushReceiverHandleManager;
import com.bearever.push.model.PushTargetEnum;
import com.bearever.push.model.ReceiverInfo;
import com.vivo.push.model.UPSNotificationMessage;
import com.vivo.push.sdk.OpenClientPushMessageReceiver;

/**
 * desc:  vivo的消息接收
 * auth:  malong
 * email: luomingbear@163.com
 * time:  2019/4/18
 **/
public class VivoBroadcastReceiver extends OpenClientPushMessageReceiver {
    @Override
    public void onNotificationMessageClicked(Context context, UPSNotificationMessage upsNotificationMessage) {
        /**
         * UPSNotificationMessage 数据结构
         *      msgid：通知 id
         *      title：通知标题
         *      content：通知内容
         *      skipContent：通知自定义内容
         *      params：自定义键值对
         */

        ReceiverInfo info = new ReceiverInfo();
        info.setTitle(upsNotificationMessage.getTitle());
        info.setContent(upsNotificationMessage.getContent());
        info.setPushTarget(PushTargetEnum.VIVO);
        info.setRawData(upsNotificationMessage);
        PushReceiverHandleManager.getInstance().onNotificationOpened(context, info);
    }

    @Override
    public void onReceiveRegId(Context context, String s) {
        ReceiverInfo info = new ReceiverInfo();
        info.setTitle("VIVO推送初始化成功");
        info.setContent(s);
        info.setPushTarget(PushTargetEnum.VIVO);
        info.setRawData(s);
        PushReceiverHandleManager.getInstance().onRegistration(context, info);
    }
}
