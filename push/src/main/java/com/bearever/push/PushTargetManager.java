package com.bearever.push;

import android.app.Application;

import com.bearever.push.model.PushTargetEnum;
import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.receiver.PushReceiverHandleManager;
import com.bearever.push.target.BasePushTargetInit;
import com.bearever.push.target.huawei.HuaweiInit;
import com.bearever.push.target.jiguang.JPushInit;
import com.bearever.push.target.meizu.MeizuInit;
import com.bearever.push.target.oppo.OppoInit;
import com.bearever.push.target.vivo.VivoInit;
import com.bearever.push.target.xiaomi.XiaomiInit;

/**
 * 初始化推送服务的管家，根据设备判断初始化哪个平台的推送服务；
 * *********
 * 注册广播接收消息
 * 实现 {@link com.bearever.push.receiver.BasePushBroadcastReceiver} 类，在manifest里面添加如下代码实现广播的接收
 * <permission
 * android:name="${applicationId}.push.RECEIVER"
 * android:protectionLevel="signature" />
 * <uses-permission android:name="${applicationId}.push.RECEIVER" />
 * <application>
 * <receiver
 * android:name=".PushBroadcastReceiverIml"
 * android:permission="${applicationId}.push.RECEIVER">
 * <intent-filter>
 * <action android:name="com.bearever.push.IPushBroadcast" />
 * </intent-filter>
 * </receiver>
 * </application>
 * --------------------
 * 另外一种是添加回调接口监听，执行{@link #addPushReceiverListener(String, OnPushReceiverListener)}添加推送回调接口
 * *********
 * Created by luoming on 2018/5/28.
 */
public class PushTargetManager {
    private static final String TAG = "PushTargetManager";
    private static PushTargetManager instance;
    /**
     * 当前的推送平台
     */
    private PushTargetEnum mTarget = PushTargetEnum.XIAOMI;
    /**
     * 当前选择的推送方式
     */
    private BasePushTargetInit mPushTarget;

    /**
     * 广播的action
     */
    public static final String ACTION = "com.bearever.push.IPushBroadcast";
    /**
     * 广播的permission
     */
    public static final String PERMISSION = ".push.RECEIVER";

    public static PushTargetManager getInstance() {
        if (instance == null) {
            synchronized (PushTargetManager.class) {
                if (instance == null) {
                    instance = new PushTargetManager();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public PushTargetManager init(Application context) {
        String mobile_brand = android.os.Build.MANUFACTURER;
        mobile_brand = mobile_brand.toUpperCase();
        //根据设备厂商选择推送平台
        //小米的使用小米推送，华为使用华为推送...其他的使用极光推送
        if (PushTargetEnum.XIAOMI.brand.equals(mobile_brand)) {
            this.mTarget = PushTargetEnum.XIAOMI;
            this.mPushTarget = new XiaomiInit(context);
        } else if (PushTargetEnum.HUAWEI.brand.equals(mobile_brand)) {
            this.mTarget = PushTargetEnum.HUAWEI;
            this.mPushTarget = new HuaweiInit(context);
        } else if (PushTargetEnum.OPPO.brand.equals(mobile_brand)) {
            this.mTarget = PushTargetEnum.OPPO;
            this.mPushTarget = new OppoInit(context);
        } else if (PushTargetEnum.VIVO.brand.equals(mobile_brand)) {
            this.mTarget = PushTargetEnum.VIVO;
            this.mPushTarget = new VivoInit(context);
        } else if (PushTargetEnum.MEIZU.brand.equals(mobile_brand)) {
            this.mTarget = PushTargetEnum.MEIZU;
            this.mPushTarget = new MeizuInit(context);
        } else {
            this.mTarget = PushTargetEnum.JPUSH;
            this.mPushTarget = new JPushInit(context);
        }
        PushReceiverHandleManager.getInstance().setPushTargetInit(mPushTarget);
        return this;
    }

    /**
     * 设置别名，华为不可用，需要通过@link 接口OnPushReceiverListener.onRegistration 方法回调取得
     *
     * @param alias 别名
     */
    public PushTargetManager setAliasExceptHuawei(String alias) {
        if (mPushTarget == null) {
            throw new NullPointerException("请先执行init()，然后在设置别名");
        }
        PushReceiverHandleManager.getInstance().setAlias(alias);
        return this;
    }

    /**
     * 添加推送监听
     *
     * @param key
     * @param listener
     */
    public void addPushReceiverListener(String key, OnPushReceiverListener listener) {
        PushReceiverHandleManager.getInstance().addPushReceiverListener(key, listener);
    }

    /**
     * 移除一个推送监听
     *
     * @param key
     */
    public void removePushReceiverListener(String key) {
        PushReceiverHandleManager.getInstance().removePushReceiverListener(key);
    }

    /**
     * 清空推送监听
     */
    public void clearPushReceiverListener() {
        PushReceiverHandleManager.getInstance().clearPushReceiverListener();

    }

    /**
     * 获取当前选择的推送平台
     *
     * @return
     */
    public PushTargetEnum getPushTarget() {
        return this.mTarget;
    }

    public interface OnPushReceiverListener {
        /**
         * 注册成功回调
         *
         * @param info info.getContent() 对应的就是具体的推送平台的注册id ，例如小米的regid和华为的TMID
         */
        void onRegister(ReceiverInfo info);

        /**
         * 别名设置完成回调
         *
         * @param info info.getContent() 对应的就是设置的别名
         */
        void onAlias(ReceiverInfo info);

        /**
         * 收到穿透消息的回调
         *
         * @param info info.getContent对应的是消息文本
         */
        void onMessage(ReceiverInfo info);

        /**
         * 收到通知的回调
         *
         * @param info info.getContent对应的是消息文本
         */
        void onNotification(ReceiverInfo info);

        /**
         * 点击通知的回调
         *
         * @param info info.getContent对应的是消息文本
         */
        void onOpened(ReceiverInfo info);
    }
}
