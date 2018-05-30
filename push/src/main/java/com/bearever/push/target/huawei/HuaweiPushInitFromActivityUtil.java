package com.bearever.push.target.huawei;

import android.app.Activity;

import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;

/**
 * 在应用的第一个activity调用这个工具类的init方法初始化
 * ***华为推送太傻比了，没办法啊***
 * Created by luoming on 2018/5/29.
 */

public class HuaweiPushInitFromActivityUtil {
    private static final String TAG = "HuaweiPushInitFromActiv";


    /**
     * 初始化华为推送
     *
     * @param activity
     */
    public static void init(Activity activity) {
        connect(activity);
    }

    private static void connect(Activity activity) {
        HMSAgent.connect(activity, new ConnectHandler() {
            @Override
            public void onConnect(int rst) {
                if (rst == HMSAgent.AgentResultCode.HMSAGENT_SUCCESS) {
                    //连接成功就获取token和设置打开推送等
                    HMSAgent.Push.getPushState(null);
                    HMSAgent.Push.getToken(null);
//                    HMSAgent.Push.enableReceiveNormalMsg(true, null);
//                    HMSAgent.Push.enableReceiveNotifyMsg(true,null);
                }
            }
        });
    }
}
