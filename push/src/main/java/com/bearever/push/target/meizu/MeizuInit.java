package com.bearever.push.target.meizu;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

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
    public MeizuInit(Application application) {
        super(application);

        String appid = ApplicationUtil.getMetaData(application, "MEIZU_APP_ID");
        String appkey = ApplicationUtil.getMetaData(application, "MEIZU_APP_KEY");
        if (TextUtils.isEmpty(appid) || TextUtils.isEmpty(appkey)) {
            return;
        }

        PushManager.register(application, appid, appkey);
    }

    /**
     * 魅族设置别名需要pushId，所以不能直接设置，需要在注册成功之后执行设置别名
     * 这里的设置功能不可用
     *
     * @param context
     * @param alias
     */
    @Override
    public void setAlias(Context context, String alias) {

    }
}
