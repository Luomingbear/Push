package com.bearever.push.target.jiguang;

import android.content.Context;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * 自定义的alin/tag设置接口回调
 * Created by luoming on 2018/5/28.
 */

public class IJPushEventReceiver extends JPushMessageReceiver {
    @Override
    public void onTagOperatorResult(Context var1, JPushMessage var2) {
    }

    @Override
    public void onCheckTagOperatorResult(Context var1, JPushMessage var2) {

    }

    @Override
    public void onAliasOperatorResult(Context var1, JPushMessage var2) {

    }

    @Override
    public void onMobileNumberOperatorResult(Context var1, JPushMessage var2) {

    }
}
