package com.bearever.push.target.xiaomi;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bearever.push.handle.PushReceiverHandleManager;
import com.bearever.push.model.PushTargetEnum;
import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.target.BasePushTargetInit;
import com.bearever.push.util.ApplicationUtil;
import com.xiaomi.mipush.sdk.MiPushClient;

/**
 * 小米推送的初始化
 * Created by luoming on 2018/5/28.
 */

public class XiaomiInit extends BasePushTargetInit {
    private static final String TAG = "XiaomiInit";

    public XiaomiInit(Application context) {
        super(context);
        //注册SDK
        String appId = ApplicationUtil.getMetaData(context, "XMPUSH_APPID");
        String appKey = ApplicationUtil.getMetaData(context, "XMPUSH_APPKEY");
        MiPushClient.registerPush(context, appId.replace(" ", ""), appKey.replace(" ", ""));
        Log.d(TAG, "初始化小米推送");
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
