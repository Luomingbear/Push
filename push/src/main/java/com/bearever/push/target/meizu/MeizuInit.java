package com.bearever.push.target.meizu;

import android.app.Application;
import android.content.Context;

import com.bearever.push.target.BasePushTargetInit;

/**
 * 魅族推送的初始化
 * 作者：luoming on 2018/9/30.
 * 邮箱：luomingbear@163.com
 * 版本：v1.0
 */
public class MeizuInit extends BasePushTargetInit {
    public MeizuInit(Application application) {
        super(application);
    }

    @Override
    public void setAlias(Context context, String alias) {

    }
}
