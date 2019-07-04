package com.bearever.push.receiver;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.bearever.push.PushTargetManager;
import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.target.BasePushTargetInit;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一处理收到的推送
 * Created by luoming on 2018/5/28.
 */
public class PushReceiverHandleManager {
    private static final String TAG = "PushReceiverHandleManag";
    private static PushReceiverHandleManager instance;

    private String mAlias = "";
    private BasePushTargetInit mPushTargetInit;

    /**
     * 待处理的消息列表
     */
    private HashMap<String, PushTargetManager.OnPushReceiverListener> mReceiverMap = new HashMap<>();

    /**
     * 注册行为
     */
    public static final int TYPE_REGISTRATION = 0;
    /**
     * 设置别名行为
     */
    public static final int TYPE_ALIAS = TYPE_REGISTRATION + 1;
    /**
     * 收到自定义消息行为
     */
    public static final int TYPE_MESSAGE = TYPE_ALIAS + 1;
    /**
     * 收到通知行为
     */
    public static final int TYPE_NOTIFICATION = TYPE_MESSAGE + 1;
    /**
     * 点击通知行为
     */
    public static final int TYPE_OPEN = TYPE_NOTIFICATION + 1;

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

    /**
     * 发送广播
     *
     * @param context
     * @param type    行为类型
     * @param info
     */
    private void sendBroadcast(Context context, int type, ReceiverInfo info) {
        Log.d(TAG, "发送广播\nPackageName:" + context.getPackageName() + "\n" + info.toString());
        Intent intent = new Intent();
        intent.putExtra("receiverinfo", new Gson().toJson(info));
        intent.putExtra("type", type);
        intent.putExtra("pushTarget", info.getPushTarget());
        intent.setAction(PushTargetManager.ACTION);

        //Android O 之后限制了隐式广播的接收，需要主动注册接收器
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            context.sendBroadcast(intent, context.getPackageName() + PushTargetManager.PERMISSION);
        } else {
            sendImplicitBroadcast(context, intent);
        }
    }

    /**
     * 发送隐式广播
     *
     * @param ctxt
     * @param i
     */
    private static void sendImplicitBroadcast(Context ctxt, Intent i) {
        PackageManager pm = ctxt.getPackageManager();
        List<ResolveInfo> matches = pm.queryBroadcastReceivers(i, 0);

        for (ResolveInfo resolveInfo : matches) {
            Intent explicit = new Intent(i);
            ComponentName cn =
                    new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName,
                            resolveInfo.activityInfo.name);
            explicit.setComponent(cn);
            ctxt.sendBroadcast(explicit, ctxt.getPackageName() + PushTargetManager.PERMISSION);
        }
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

        //执行设置别名操作
        doSetAlias(context, info);

        //发送广播
        sendBroadcast(context, TYPE_REGISTRATION, info);
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

        //发送广播
        sendBroadcast(context, TYPE_ALIAS, info);
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

        //发送广播
        sendBroadcast(context, TYPE_MESSAGE, info);
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

        //发送广播
        sendBroadcast(context, TYPE_NOTIFICATION, info);
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

        //发送广播
        sendBroadcast(context, TYPE_OPEN, info);
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
