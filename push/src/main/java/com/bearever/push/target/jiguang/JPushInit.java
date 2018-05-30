package com.bearever.push.target.jiguang;

import android.app.Application;
import android.content.Context;

import com.bearever.push.handle.PushReceiverHandleManager;
import com.bearever.push.handle.ReceiverInfo;
import com.bearever.push.init.BasePushTargetInit;
import com.bearever.push.util.ApplicationUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * 极光推送的初始化服务
 * Created by luoming on 2018/5/28.
 */

public class JPushInit extends BasePushTargetInit {
    public JPushInit(Application application) {
        super(application);
        JPushInterface.init(application);

    }
}
