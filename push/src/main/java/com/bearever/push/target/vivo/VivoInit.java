package com.bearever.push.target.vivo;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.bearever.push.model.PushTargetEnum;
import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.receiver.PushReceiverHandleManager;
import com.bearever.push.target.BasePushTargetInit;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;

/**
 * desc:  vivo推送初始化类
 * auth:  malong
 * email: luomingbear@163.com
 * time:  2019/4/18
 **/
public class VivoInit extends BasePushTargetInit {
    private static final String TAG = "VivoInit";
    private int mInitCount = 0;
    private int mAliasCount = 0;
    private Handler mHandler;

    public VivoInit(final Application application) {
        super(application);
        if (!PushClient.getInstance(application).isSupport()) {
            return;
        }

        //初始化
        PushClient.getInstance(application).initialize();
        if (mHandler == null) {
            mHandler = new Handler();
        }
        mHandler.post(initRunnable);
        Log.d(TAG, "初始化魅族推送");
    }

    private Runnable initRunnable = new Runnable() {
        @Override
        public void run() {
            //打开推送
            PushClient.getInstance(mApplication).turnOnPush(new IPushActionListener() {
                @Override
                public void onStateChanged(int i) {
                    if (i != 0 && i != 1) {
                        //失败重试
                        if (mInitCount < MAX_RETRY_COUNT) {
                            if (mHandler == null) {
                                mHandler = new Handler();
                            }
                            mHandler.postDelayed(initRunnable, 1000);
                            mInitCount++;
                        }
                    }
                }
            });
        }
    };

    @Override
    public void setAlias(Context context, String alias, ReceiverInfo registerInfo) {
        super.setAlias(context, alias, registerInfo);
        if (mHandler == null) {
            mHandler = new Handler();
        }
        mHandler.post(aliasRunnable);
    }

    private Runnable aliasRunnable = new Runnable() {
        @Override
        public void run() {
            //设置别名
            PushClient.getInstance(mApplication).bindAlias(mAlias, new IPushActionListener() {
                @Override
                public void onStateChanged(int i) {
                    if (i != 0 && i != 1) {
                        //失败重试
                        if (mAliasCount < MAX_RETRY_COUNT) {
                            if (mHandler == null) {
                                mHandler = new Handler();
                            }
                            mHandler.postDelayed(aliasRunnable, 1000);
                            mAliasCount++;
                        }
                    } else {
                        //成功
                        ReceiverInfo info = new ReceiverInfo();
                        info.setContent(mAlias);
                        info.setPushTarget(PushTargetEnum.VIVO);
                        PushReceiverHandleManager.getInstance().onAliasSet(mApplication, info);
                    }
                }
            });
        }
    };

}
