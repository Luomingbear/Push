package com.bearever.push.target.xiaomi;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.bearever.push.model.PushTargetEnum;
import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.receiver.PushReceiverHandleManager;
import com.bearever.push.target.BasePushTargetInit;
import com.bearever.push.util.ApplicationUtil;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

/**
 * 小米推送的初始化
 * Created by luoming on 2018/5/28.
 */

public class XiaomiInit extends BasePushTargetInit {
    private static final String TAG = "XiaomiInit";

    public XiaomiInit(Application context) {
        super(context);

        if (shouldInit()) {
            //注册SDK
            String appId = ApplicationUtil.getMetaData(context, "XMPUSH_APPID");
            String appKey = ApplicationUtil.getMetaData(context, "XMPUSH_APPKEY");

            MiPushClient.registerPush(context, appId.replaceAll(" ", ""),
                    appKey.replaceAll(" ", ""));

            Log.d(TAG, "初始化小米推送");
        }
    }

    private boolean shouldInit() {
        ActivityManager am = (ActivityManager) mApplication.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = mApplication.getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setAlias(Context context, String alias, ReceiverInfo registerInfo) {
        MiPushClient.setAlias(context, alias, null);
        ReceiverInfo aliasInfo = new ReceiverInfo();
        aliasInfo.setContent(alias);
        aliasInfo.setPushTarget(PushTargetEnum.XIAOMI);
        PushReceiverHandleManager.getInstance().onAliasSet(context, aliasInfo);
    }
}
