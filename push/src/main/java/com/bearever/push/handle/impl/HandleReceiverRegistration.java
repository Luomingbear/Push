package com.bearever.push.handle.impl;

import android.content.Context;
import android.util.Log;

import com.bearever.push.handle.PushReceiverHandleManager;
import com.bearever.push.model.PushTarget;
import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.util.ApplicationUtil;
import com.xiaomi.mipush.sdk.MiPushClient;

import cn.jpush.android.api.JPushInterface;

/**
 * 处理用户注册SDK
 * 设置别名
 * Created by luoming on 2018/5/28.
 */

public class HandleReceiverRegistration implements BaseHandleListener {
    private static final String TAG = "HandleReceiverRegistrat";

    @Override
    public void handle(Context context, ReceiverInfo info) {
        Log.i(TAG, "handle: " + info.getContent());
//        当SDK注册完成之后，自动执行设置别名
//        setAlias(context, info);
    }

    /**
     * 设置别名，默认别名使用的是设备ID
     *
     * @param context
     * @param info
     */
    private void setAlias(Context context, ReceiverInfo info) {
        String alias = ApplicationUtil.getDeviceId(context);
        if (info.getPushTarget() == PushTarget.JPUSH) {
            //极光
            JPushInterface.setAlias(context, 0, alias);
            ReceiverInfo aliasInfo = new ReceiverInfo();
            aliasInfo.setContent(alias);
            PushReceiverHandleManager.getInstance().onAliasSet(context, aliasInfo);
        } else if (info.getPushTarget() == PushTarget.XIAOMI) {
            //小米
            MiPushClient.setAlias(context, alias, null);
            ReceiverInfo aliasInfo = new ReceiverInfo();
            aliasInfo.setContent(alias);
            PushReceiverHandleManager.getInstance().onAliasSet(context, aliasInfo);
        } else if (info.getPushTarget() == PushTarget.HUAWEI) {
            //华为不能主动设置别名，只能获取华为的token
            //此处不需要添加代码
        }
    }

}
