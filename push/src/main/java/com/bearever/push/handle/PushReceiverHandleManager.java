package com.bearever.push.handle;

import android.content.Context;
import android.text.TextUtils;

import com.bearever.push.PushTargetManager;
import com.bearever.push.handle.impl.BaseHandleListener;
import com.bearever.push.handle.impl.HandleReceiverAlias;
import com.bearever.push.handle.impl.HandleReceiverMessage;
import com.bearever.push.handle.impl.HandleReceiverNotification;
import com.bearever.push.handle.impl.HandleReceiverNotificationOpened;
import com.bearever.push.handle.impl.HandleReceiverRegistration;
import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.target.BasePushTargetInit;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一处理收到的推送
 * Created by luoming on 2018/5/28.
 */

public class PushReceiverHandleManager {
    private static final String TAG = "PushReceiverHandleManag";
    private static PushReceiverHandleManager instance;

    private BaseHandleListener mMessageHandle;
    private BaseHandleListener mNotificationHandle;
    private BaseHandleListener mNotificationOpenedHandle;
    private BaseHandleListener mSDKRegistrationHandle;
    private BaseHandleListener mAliasSetHandle;
    private String mAlias = ""; //别名
    private BasePushTargetInit mPushTargetInit; //推送平台初始化工具

    private HashMap<String, PushTargetManager.OnPushReceiverListener> mReceiverMap = new HashMap<>(); //待处理的消息列表

    public static PushReceiverHandleManager getInstance() {
        if (instance == null) {
            synchronized (PushReceiverHandleManager.class) {
                if (instance == null) {
                    instance = new PushReceiverHandleManager();
                }
            }
        }
        return instance;
    }

    private PushReceiverHandleManager() {

    }

    public void setPushTargetInit(BasePushTargetInit pushTargetInit) {
        this.mPushTargetInit = pushTargetInit;
    }

    /***
     * 用户注册sdk之后的通知
     * 注册成功之后设置别名
     * @param context
     */
    public void onRegistration(Context context, ReceiverInfo info) {
        for (Map.Entry<String, PushTargetManager.OnPushReceiverListener> item : mReceiverMap.entrySet()) {
            PushTargetManager.OnPushReceiverListener listener = item.getValue();
            if (listener != null) {
                listener.onRegister(info);
            }
        }

        if (mSDKRegistrationHandle == null) {
            mSDKRegistrationHandle = new HandleReceiverRegistration();
        }
        mSDKRegistrationHandle.handle(context, info);
        //执行设置别名操作
        doSetAlias(context, info);
    }

    /**
     * 执行别名设置
     */
    private void doSetAlias(Context context, ReceiverInfo registerInfo) {
        if (TextUtils.isEmpty(mAlias) || mPushTargetInit == null) {
            return;
        }
        mPushTargetInit.setAlias(context, mAlias, registerInfo);
    }

    /**
     * 设置了别名之后
     *
     * @param context
     * @param info
     */
    public void onAliasSet(Context context, ReceiverInfo info) {
        for (Map.Entry<String, PushTargetManager.OnPushReceiverListener> item : mReceiverMap.entrySet()) {
            PushTargetManager.OnPushReceiverListener listener = item.getValue();
            if (listener != null) {
                listener.onAlias(info);
            }
        }
        if (mAliasSetHandle == null) {
            mAliasSetHandle = new HandleReceiverAlias();
        }
        mAliasSetHandle.handle(context, info);
    }

    /**
     * 接收到消息推送，不会主动显示在通知栏
     *
     * @param context
     * @param info
     */
    public void onMessageReceived(Context context, ReceiverInfo info) {
        for (Map.Entry<String, PushTargetManager.OnPushReceiverListener> item : mReceiverMap.entrySet()) {
            PushTargetManager.OnPushReceiverListener listener = item.getValue();
            if (listener != null) {
                listener.onMessage(info);
            }
        }
        if (mMessageHandle == null) {
            mMessageHandle = new HandleReceiverMessage();
        }
        mMessageHandle.handle(context, info);
    }

    /**
     * 接收到通知，会主动显示在通知栏的
     *
     * @param context
     * @param info
     */
    public void onNotificationReceived(Context context, ReceiverInfo info) {
        for (Map.Entry<String, PushTargetManager.OnPushReceiverListener> item : mReceiverMap.entrySet()) {
            PushTargetManager.OnPushReceiverListener listener = item.getValue();
            if (listener != null) {
                listener.onNotification(info);
            }
        }
        if (mNotificationHandle == null) {
            mNotificationHandle = new HandleReceiverNotification();
        }
        mNotificationHandle.handle(context, info);
    }

    /**
     * 用户点击了通知
     *
     * @param context
     * @param info
     */
    public void onNotificationOpened(Context context, ReceiverInfo info) {
        for (Map.Entry<String, PushTargetManager.OnPushReceiverListener> item : mReceiverMap.entrySet()) {
            PushTargetManager.OnPushReceiverListener listener = item.getValue();
            if (listener != null) {
                listener.onOpened(info);
            }
        }
        if (mNotificationOpenedHandle == null) {
            mNotificationOpenedHandle = new HandleReceiverNotificationOpened();
        }
        mNotificationOpenedHandle.handle(context, info);
    }

    /**
     * 添加推送监听
     *
     * @param key
     * @param listener
     */
    public void addPushReceiverListener(String key, PushTargetManager.OnPushReceiverListener listener) {
        mReceiverMap.put(key, listener);
    }

    /**
     * 移除一个推送监听
     *
     * @param key
     */
    public void removePushReceiverListener(String key) {
        if (mReceiverMap.containsKey(key)) {
            mReceiverMap.remove(key);
        }
    }

    /**
     * 清空推送监听
     */
    public void clearPushReceiverListener() {
        mReceiverMap.clear();
    }

    public String getAlias() {
        return mAlias;
    }

    /**
     * 设置别名
     * 对于华为手机无效，华为手机只能通过返回的token识别用户
     *
     * @param mAlias
     */
    public void setAlias(String mAlias) {
        this.mAlias = mAlias;
    }

}
