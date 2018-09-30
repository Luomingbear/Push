package com.bearever.push.target.xiaomi;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bearever.push.BuildConfig;
import com.bearever.push.handle.PushReceiverHandleManager;
import com.bearever.push.model.PushTarget;
import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.target.BasePushTargetInit;
import com.bearever.push.util.ApplicationUtil;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

/**
 * 小米推送的初始化
 * Created by luoming on 2018/5/28.
 */

public class XiaomiInit extends BasePushTargetInit {
    private static final String TAG = "mipush";

    public XiaomiInit(Application context) {
        super(context);
        //注册SDK
        String appId = ApplicationUtil.getMetaData(context, "XMPUSH_APPID");
        String appKey = ApplicationUtil.getMetaData(context, "XMPUSH_APPKEY");
        MiPushClient.registerPush(context, appId.replace(" ", ""), appKey.replace(" ", ""));

        //调试
        if (BuildConfig.DEBUG) {
            LoggerInterface newLogger = new LoggerInterface() {
                @Override
                public void setTag(String tag) {
                    // ignore
                }

                @Override
                public void log(String content, Throwable t) {
                    Log.d(TAG, content, t);
                }

                @Override
                public void log(String content) {
                    Log.d(TAG, content);
                }
            };
            Logger.setLogger(context, newLogger);
        }
    }

    @Override
    public void setAlias(Context context, String alias) {
        MiPushClient.setAlias(context, alias, null);
        ReceiverInfo aliasInfo = new ReceiverInfo();
        aliasInfo.setContent(alias);
        aliasInfo.setPushTarget(PushTarget.XIAOMI);
        PushReceiverHandleManager.getInstance().onAliasSet(context, aliasInfo);
    }
}
