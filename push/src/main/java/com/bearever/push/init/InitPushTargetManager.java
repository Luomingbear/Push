package com.bearever.push.init;

import android.app.Application;
import android.content.Context;

import com.bearever.push.target.huawei.HuaweiInit;
import com.bearever.push.target.jiguang.JPushInit;
import com.bearever.push.target.xiaomi.XiaomiInit;

/**
 * 初始化推送服务的管家，根据设备判断初始化哪个平台的推送服务
 * Created by luoming on 2018/5/28.
 */

public class InitPushTargetManager {
    private static InitPushTargetManager instance;

    private String HUAWEI = "HUAWEI";
    private String XIAOMI = "Xiaomi";

    public static InitPushTargetManager getInstance() {
        if (instance == null) {
            instance = new InitPushTargetManager();
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
        BasePushTargetInit pushInit;

        //根据设备厂商选择推送平台
        //小米的使用小米推送，华为使用华为推送，其他的使用极光推送
        if (XIAOMI.equals(mobile_brand)) {
            pushInit = new XiaomiInit(context);
        } else if (HUAWEI.equals(mobile_brand)) {
            pushInit = new HuaweiInit(context);
        } else {
            pushInit = new JPushInit(context);
        }
    }
}
