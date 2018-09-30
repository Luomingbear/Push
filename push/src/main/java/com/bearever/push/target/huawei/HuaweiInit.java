package com.bearever.push.target.huawei;

import android.app.Application;
import android.content.Context;

import com.bearever.push.target.BasePushTargetInit;
import com.huawei.android.hms.agent.HMSAgent;

/**
 * 华为推送初始化
 * Created by luoming on 2018/5/28.
 */

public class HuaweiInit extends BasePushTargetInit {
    public HuaweiInit(Application application) {
        super(application);

        HMSAgent.init(application);
    }

    @Override
    public void setAlias(Context context, String alias) {
        //华为没有设置alias的功能
    }
}
