package com.bearever.push.target;

import android.app.Application;
import android.content.Context;

import com.bearever.push.model.ReceiverInfo;

/**
 * 初始化推送平台的基类
 * Created by luoming on 2018/5/28.
 */

public abstract class BasePushTargetInit {
    protected static int MAX_RETRY_COUNT = 3;
    protected Application mApplication;
    protected String mAlias;

    /**
     * 推送初始化
     *
     * @param application
     */
    public BasePushTargetInit(Application application) {
        this.mApplication = application;
    }

    /**
     * 设置别名
     *
     * @param context
     * @param alias
     */
    public void setAlias(Context context, String alias, ReceiverInfo registerInfo) {
        this.mAlias = alias;
    }
}
