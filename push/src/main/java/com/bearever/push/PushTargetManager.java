package com.bearever.push;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.bearever.push.handle.PushReceiverHandleManager;
import com.bearever.push.model.PushTarget;
import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.target.huawei.HuaweiInit;
import com.bearever.push.target.jiguang.JPushInit;
import com.bearever.push.target.xiaomi.XiaomiInit;
import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.xiaomi.mipush.sdk.MiPushClient;

import cn.jpush.android.api.JPushInterface;

/**
 * 初始化推送服务的管家，根据设备判断初始化哪个平台的推送服务；
 * *********
 * 如果你需要对接收到的信息进行处理，请实现 handle/impl里面的类：
 * HandleReceiverAlias----------------别名设置之后执行
 * HandleReceiverMessage--------------接收到消息之后执行，不会主动显示通知
 * HandleReceiverNotification---------接收到消息之后执行，主动显示通知
 * HandleReceiverNotificationOpened---用户点击了通知之后执行
 * *********
 * Created by luoming on 2018/5/28.
 */

public class PushTargetManager {
    private static PushTargetManager instance;

    private PushTarget mTarget = PushTarget.JPUSH; //当前的推送平台

    //设备厂商名
    private String HUAWEI = "HUAWEI"; //华为
    private String XIAOMI = "Xiaomi"; //小米

    public static PushTargetManager getInstance() {
        if (instance == null) {
            instance = new PushTargetManager();
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Application context) {
        String mobile_brand = android.os.Build.MANUFACTURER;

        //根据设备厂商选择推送平台
        //小米的使用小米推送，华为使用华为推送，其他的使用极光推送
        if (XIAOMI.equals(mobile_brand)) {
            this.mTarget = PushTarget.XIAOMI;
            new XiaomiInit(context);
        } else if (HUAWEI.equals(mobile_brand)) {
            this.mTarget = PushTarget.HUAWEI;
            new HuaweiInit(context);
        } else {
            this.mTarget = PushTarget.JPUSH;
            new JPushInit(context);
        }
    }

    /**
     * 初始化华为推送
     *
     * @param activity
     */
    public void initHuaweiPush(Activity activity) {
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

    /**
     * 设置别名，华为不可用
     *
     * @param context
     * @param alias   别名
     */
    public void setAliasNotWithHuawei(Context context, String alias) {
        String mobile_brand = android.os.Build.MANUFACTURER;
        if (XIAOMI.equals(mobile_brand)) {
            //小米
            MiPushClient.setAlias(context, alias, null);
            ReceiverInfo aliasInfo = new ReceiverInfo();
            aliasInfo.setContent(alias);
            aliasInfo.setPushTarget(PushTarget.XIAOMI);
            PushReceiverHandleManager.getInstance().onAliasSet(context, aliasInfo);
        } else if (HUAWEI.equals(mobile_brand)) {
            //华为不能主动设置别名，只能获取华为的token
            //此处不需要添加代码

        } else {
            //极光
            JPushInterface.setAlias(context, 0, alias);
            ReceiverInfo aliasInfo = new ReceiverInfo();
            aliasInfo.setContent(alias);
            aliasInfo.setPushTarget(PushTarget.JPUSH);
            PushReceiverHandleManager.getInstance().onAliasSet(context, aliasInfo);
        }
    }

    /**
     * 获取当前选择的推送平台
     *
     * @return
     */
    public PushTarget getPushTarget() {
        return this.mTarget;
    }
}
