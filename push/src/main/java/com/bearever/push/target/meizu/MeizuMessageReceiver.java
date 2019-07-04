package com.bearever.push.target.meizu;

import android.content.Context;

import com.bearever.push.receiver.PushReceiverHandleManager;
import com.bearever.push.model.PushTargetEnum;
import com.bearever.push.model.ReceiverInfo;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;

/**
 * 魅族消息的接收
 * 作者：luoming on 2018/10/8.
 * 邮箱：luomingbear@163.com
 * 版本：v1.0
 */
public class MeizuMessageReceiver extends MzPushMessageReceiver {
    @Override
    @Deprecated
    public void onRegister(Context context, String pushid) {
        //调用 PushManager.register(context）方法后，会在此回调注册状态
        //应用在接受返回的 pushid
    }

    @Override
    public void onMessage(Context context, String s) {
        //接收服务器推送的透传消息
        ReceiverInfo info = new ReceiverInfo();
        info.setPushTarget(PushTargetEnum.MEIZU);
        info.setContent(s);
        PushReceiverHandleManager.getInstance().onMessageReceived(context, info);
    }

    @Override
    @Deprecated
    public void onUnRegister(Context context, boolean b) {
        //调用 PushManager.unRegister(context）方法后，会在此回调反注册状态
    }

    @Override
    public void onPushStatus(Context context, PushSwitchStatus
            pushSwitchStatus) {
        //检查通知栏和透传消息开关状态回调
    }

    @Override
    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
        //调用新版订阅 PushManager.register(context,appId,appKey)回调
        if (registerStatus.code.equals(RegisterStatus.SUCCESS_CODE)) {
            ReceiverInfo info = new ReceiverInfo();
            info.setPushTarget(PushTargetEnum.MEIZU);
            info.setTitle("魅族推送注册成功");
            info.setContent(registerStatus.getPushId());
            info.setRawData(registerStatus);
            PushReceiverHandleManager.getInstance().onRegistration(context, info);
        }
    }

    @Override
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {
        //新版反订阅回调
    }

    @Override
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {
        //标签回调
    }

    @Override
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {
        //别名回调
        ReceiverInfo info = new ReceiverInfo();
        info.setPushTarget(PushTargetEnum.MEIZU);
        info.setContent(subAliasStatus.getAlias());
        info.setRawData(subAliasStatus);
        PushReceiverHandleManager.getInstance().onAliasSet(context, info);
    }

    @Override
    public void onNotificationArrived(Context context, MzPushMessage mzPushMessage) {
        //通知栏消息到达回调，flyme6 基于 android6.0 以上不再回调
        PushReceiverHandleManager.getInstance().onNotificationReceived(context, createReceiverInfo(mzPushMessage));
    }

    @Override
    public void onNotificationClicked(Context context, MzPushMessage mzPushMessage) {
        //通知栏消息点击回调
        PushReceiverHandleManager.getInstance().onNotificationOpened(context, createReceiverInfo(mzPushMessage));
    }

    @Override
    public void onNotificationDeleted(Context context, MzPushMessage mzPushMessage) {
        //通知栏消息删除回调；flyme6 基于 android6.0 以上不再回调
    }

    private ReceiverInfo createReceiverInfo(MzPushMessage mzPushMessage) {
        ReceiverInfo info = new ReceiverInfo();
        info.setPushTarget(PushTargetEnum.MEIZU);
        info.setContent(mzPushMessage.getContent());
        info.setTitle(mzPushMessage.getTitle());
        info.setExtra(mzPushMessage.getSelfDefineContentString());
        info.setRawData(mzPushMessage);
        return info;
    }
}
