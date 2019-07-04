package com.bearever.push.target.oppo;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.bearever.push.model.PushTargetEnum;
import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.receiver.PushReceiverHandleManager;
import com.bearever.push.target.BasePushTargetInit;
import com.bearever.push.util.ApplicationUtil;
import com.coloros.mcssdk.PushManager;
import com.coloros.mcssdk.callback.PushCallback;
import com.coloros.mcssdk.mode.ErrorCode;
import com.coloros.mcssdk.mode.SubscribeResult;

import java.util.List;

/**
 * oppo的推送服务初始化
 * 作者：luoming on 2018/9/30.
 * 邮箱：luomingbear@163.com
 * 版本：v1.0
 */
public class OppoInit extends BasePushTargetInit {
    private static final String TAG = "OppoInit";
    private int mInitCount = 0;
    private int mAliasCount = 0;
    private Handler mHandler;

    private Runnable mInitRunnable = new Runnable() {
        @Override
        public void run() {
            if (mInitCount < MAX_RETRY_COUNT) {
                init();
                mInitCount++;
            }
        }
    };

    private Runnable mAliasRunnable = new Runnable() {
        @Override
        public void run() {
            if (mAliasCount < MAX_RETRY_COUNT) {
                setAlias(mApplication, mAlias, null);
                mAliasCount++;
            }
        }
    };

    public OppoInit(Application application) {
        super(application);
        if (!PushManager.isSupportPush(application)) {
            return;
        }
        init();
        Log.d(TAG, "初始化OPPO推送");
    }

    private void init() {
        String appKey = ApplicationUtil.getMetaData(mApplication, "OPPO_APP_KEY");
        String appSecret = ApplicationUtil.getMetaData(mApplication, "OPPO_APP_SECRET");
        PushManager.getInstance().register(mApplication, appKey, appSecret, pushCallback);
    }

    private PushCallback pushCallback = new PushCallback() {
        @Override
        public void onRegister(int i, String s) {
            //s 注册id，用来唯一标识设备的
            if (i == ErrorCode.SUCCESS) {
                //注册成功
                ReceiverInfo info = new ReceiverInfo();
                info.setContent(s);
                info.setTitle("OPPO注册成功");
                info.setRawData(s);
                info.setPushTarget(PushTargetEnum.OPPO);
                PushReceiverHandleManager.getInstance().onRegistration(mApplication, info);
            } else {
                if (mHandler == null) {
                    mHandler = new Handler();
                }
                mHandler.postDelayed(mInitRunnable, 1000);
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
                if (list != null && list.size() > 0) {
                    aliasInfo.setContent(list.get(0).getContent());
                }
                aliasInfo.setRawData(list);
                aliasInfo.setPushTarget(PushTargetEnum.OPPO);
                PushReceiverHandleManager.getInstance().onAliasSet(mApplication, aliasInfo);
            } else {
                if (mHandler == null) {
                    mHandler = new Handler();
                }
                mHandler.postDelayed(mAliasRunnable, 1000);
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
    public void setAlias(Context context, String alias, ReceiverInfo registerInfo) {
        super.setAlias(context, alias, registerInfo);
//        别名设置总是失败，索性取消，使用注册id识别用户
//        List<String> list = new ArrayList<>();
//        list.add(alias);
//        PushManager.getInstance().setAliases(list);
        PushReceiverHandleManager.getInstance().onAliasSet(context, registerInfo);
    }
}
