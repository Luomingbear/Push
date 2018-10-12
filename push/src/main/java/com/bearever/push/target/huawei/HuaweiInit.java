package com.bearever.push.target.huawei;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.target.BasePushTargetInit;
import com.huawei.android.hms.agent.HMSAgent;

/**
 * 华为推送初始化
 * Created by luoming on 2018/5/28.
 */

public class HuaweiInit extends BasePushTargetInit {
    private static final String TAG = "HuaweiInit";
    private int mCount = 0;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mCount < MAX_RETRY_COUNT) {
                init();
            }
            mCount++;
        }
    };

    private Handler mHandler;

    public HuaweiInit(Application application) {
        super(application);
        init();
        Log.d(TAG, "初始化华为推送");

    }

    private void init() {
        if (!HMSAgent.init(mApplication)) {
            if (mHandler == null) {
                mHandler = new Handler();
            }
            mHandler.postDelayed(mRunnable, 1000);
        }
    }

    @Override
    public void setAlias(Context context, String alias, ReceiverInfo registerInfo) {
        //华为没有设置alias的功能
    }
}
