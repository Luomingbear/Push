package com.bearever.push.target.jiguang;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.bearever.push.handle.PushReceiverHandleManager;
import com.bearever.push.model.PushTarget;
import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.target.BasePushTargetInit;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 极光推送的初始化服务
 * Created by luoming on 2018/5/28.
 */

public class JPushInit extends BasePushTargetInit {
    private Handler mHandler;
    private int mIndex = 0;

    public JPushInit(Application application) {
        super(application);
        JPushInterface.init(application);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            setAlias(mApplication, mAlias);
        }
    };

    @Override
    public void setAlias(final Context context, String alias) {
        JPushInterface.setAlias(context, alias, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                if (i != 0) {
                    //不成功
                    if (mHandler == null) {
                        mHandler = new Handler();
                    }
                    if (mIndex < MAX_RETRY_COUNT) {
                        mIndex++;
                        mHandler.postDelayed(mRunnable, 1000);
                    }
                } else {
                    //成功
                    ReceiverInfo aliasInfo = new ReceiverInfo();
                    aliasInfo.setContent(s);
                    aliasInfo.setPushTarget(PushTarget.JPUSH);
                    PushReceiverHandleManager.getInstance().onAliasSet(context, aliasInfo);
                }
            }
        });

    }
}
