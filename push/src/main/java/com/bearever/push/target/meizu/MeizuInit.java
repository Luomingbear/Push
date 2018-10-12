package com.bearever.push.target.meizu;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.target.BasePushTargetInit;
import com.bearever.push.util.ApplicationUtil;
import com.meizu.cloud.pushsdk.PushManager;

/**
 * 魅族推送的初始化
 * 作者：luoming on 2018/9/30.
 * 邮箱：luomingbear@163.com
 * 版本：v1.0
 */
public class MeizuInit extends BasePushTargetInit {
    private static final String TAG = "MeizuInit";

    public MeizuInit(Application application) {
        super(application);

        String appid = ApplicationUtil.getMetaData(application, "MEIZU_APP_ID");
        appid = appid.replace(" ", "");
        String appkey = ApplicationUtil.getMetaData(application, "MEIZU_APP_KEY");
        if (TextUtils.isEmpty(appid) || TextUtils.isEmpty(appkey)) {
            return;
        }

        PushManager.register(application, appid, appkey);
        Log.d(TAG, "初始化魅族推送");
    }

    /**
     * 魅族设置别名需要pushId，所以不能直接设置，需要在注册成功之后执行设置别名
     * 这里的设置功能不可用
     *
     * @param context
     * @param alias
     */
    @Override
    public void setAlias(Context context, String alias, ReceiverInfo registerInfo) {
        super.setAlias(context, alias, registerInfo);
        String appid = ApplicationUtil.getMetaData(context, "MEIZU_APP_ID");
        appid = appid.replace(" ", "");
        String appkey = ApplicationUtil.getMetaData(context, "MEIZU_APP_KEY");
        PushManager.subScribeAlias(context, appid, appkey, registerInfo.getContent(), alias);
    }
}
