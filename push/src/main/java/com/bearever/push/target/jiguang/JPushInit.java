package com.bearever.push.target.jiguang;

import android.app.Application;

import com.bearever.push.target.BasePushTargetInit;

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
