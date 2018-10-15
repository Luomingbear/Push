package com.bearever.push.target.jiguang;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.target.BasePushTargetInit;

import cn.jpush.android.api.JPushInterface;

/**
 * 极光推送的初始化服务
 * Created by luoming on 2018/5/28.
 */

public class JPushInit extends BasePushTargetInit {
    private static final String TAG = "JPushInit";

    public JPushInit(Application application) {
        super(application);
        JPushInterface.init(application);
        Log.d(TAG, "初始化极光推送");
    }

    @Override
    public void setAlias(Context context, String alias, ReceiverInfo registerInfo) {
        JPushInterface.setAlias(context, 0, alias);
    }
}
