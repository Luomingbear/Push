package com.bearever.push.target.oppo;

import android.app.Application;
import android.content.Context;

import com.bearever.push.handle.PushReceiverHandleManager;
import com.bearever.push.model.PushTarget;
import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.target.BasePushTargetInit;
import com.bearever.push.util.ApplicationUtil;
import com.coloros.mcssdk.PushManager;
import com.coloros.mcssdk.callback.PushCallback;
import com.coloros.mcssdk.mode.ErrorCode;
import com.coloros.mcssdk.mode.SubscribeResult;

import java.util.ArrayList;
import java.util.List;

/**
 * oppo的推送服务初始化
 * 作者：luoming on 2018/9/30.
 * 邮箱：luomingbear@163.com
 * 版本：v1.0
 */
public class OppoInit extends BasePushTargetInit {
    public OppoInit(Application application) {
        super(application);
        // TODO: 2018/9/30 有些手机不支持推送 目前支持ColorOS3.1及以上的系统
        String appKey = ApplicationUtil.getMetaData(application, "OPPO_APP_KEY");
        String appSecret = ApplicationUtil.getMetaData(application, "OPPO_APP_SECRET");
        PushManager.getInstance().register(application.getApplicationContext(), appKey, appSecret, pushCallback);
    }

    private PushCallback pushCallback = new PushCallback() {
        @Override
        public void onRegister(int i, String s) {
            //s 注册id，用来唯一标识设备的
            if (i == ErrorCode.SUCCESS) {
                //注册成功
                ReceiverInfo info = new ReceiverInfo();
                info.setContent("Oppo推送注册成功");
                info.setRawData(s);
                info.setPushTarget(PushTarget.OPPO);
                PushReceiverHandleManager.getInstance().onRegistration(mApplication, info);
            } else {

            }
        }

        @Override
        public void onUnRegister(int i) {

        }

        @Override
        public void onGetAliases(int i, List<SubscribeResult> list) {

        }

        @Override
        public void onSetAliases(int i, List<SubscribeResult> list) {
            if (i == ErrorCode.SUCCESS) {
                //成功
                ReceiverInfo aliasInfo = new ReceiverInfo();
                aliasInfo.setContent(list.get(0).getContent());
                aliasInfo.setPushTarget(PushTarget.OPPO);
                PushReceiverHandleManager.getInstance().onAliasSet(mApplication, aliasInfo);
            }
        }

        @Override
        public void onUnsetAliases(int i, List<SubscribeResult> list) {

        }

        @Override
        public void onSetUserAccounts(int i, List<SubscribeResult> list) {

        }

        @Override
        public void onUnsetUserAccounts(int i, List<SubscribeResult> list) {

        }

        @Override
        public void onGetUserAccounts(int i, List<SubscribeResult> list) {

        }

        @Override
        public void onSetTags(int i, List<SubscribeResult> list) {

        }

        @Override
        public void onUnsetTags(int i, List<SubscribeResult> list) {

        }

        @Override
        public void onGetTags(int i, List<SubscribeResult> list) {

        }

        @Override
        public void onGetPushStatus(int i, int i1) {

        }

        @Override
        public void onSetPushTime(int i, String s) {

        }

        @Override
        public void onGetNotificationStatus(int i, int i1) {

        }
    };

    @Override
    public void setAlias(Context context, String alias) {
        List<String> list = new ArrayList<>();
        list.add(alias);
        PushManager.getInstance().setAliases(list);
    }
}
