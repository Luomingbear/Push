package com.bearever.push.handle.impl;

import android.content.Context;
import android.util.Log;

import com.bearever.push.model.ReceiverInfo;

/**
 * 处理用户点击通知栏的操作
 * Created by luoming on 2018/5/28.
 */

public class HandleReceiverNotificationOpened implements BaseHandleListener {
    private static final String TAG = "HandleReceiverNotiOpen";

    @Override
    public void handle(Context context, ReceiverInfo info) {
        Log.d(TAG, "handle: " + info.getContent());
//        openNotification(context, info);
    }

//    private void openNotification(Context context, ReceiverInfo info) {
//        if (isCurrentAppRunning(context)) {//应用running 前台 后台
//            goNextByFront(context);
//        } else {//应用没有启动 或者被回收
//            goNextByBackground(context);
//        }
//    }

//    /**
//     * 判断应用是否running
//     */
//    public boolean isCurrentAppRunning(Context context) {
//        int pid = android.os.Process.myPid();
//        ActivityManager mActivityManager = (ActivityManager) context
//                .getSystemService(Context.ACTIVITY_SERVICE);
//        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
//                .getRunningAppProcesses()) {
//            if (appProcess.pid == pid) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 应用没有被杀死打开应用
//     *
//     * @param mContext
//     */
//    public void goNextByFront(Context mContext) {
//        Intent intent = new Intent(); // 从启动动画ui跳转到主ui
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        intent.setAction(Intent.ACTION_MAIN);
//        intent.setClassName(mContext.getPackageName(), "");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//        mContext.startActivity(intent);
//    }
//
//    /**
//     * 应用已经不存在打开应用
//     *
//     * @param context
//     */
//    public void goNextByBackground(Context context) {
//        Intent launchIntent = context.getPackageManager().
//                getLaunchIntentForPackage("");
//        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//        context.startActivity(launchIntent);
//    }

}
