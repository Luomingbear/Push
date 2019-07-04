package com.bearever.push.model;

import java.io.Serializable;

/**
 * 接收到的推送的消息
 * Created by luoming on 2018/5/28.
 */
public class ReceiverInfo implements Serializable {
    private PushTargetEnum pushTarget = PushTargetEnum.JPUSH; //推送平台
    private String title = ""; //标题
    private String content = ""; //内容
    private String extra = ""; //额外数据
    private Object rawData; //原始数据

    public ReceiverInfo() {
    }

    public ReceiverInfo(PushTargetEnum pushTarget, String title, String content, String extra,
                        Object rawData) {
        this.pushTarget = pushTarget;
        this.title = title;
        this.content = content;
        this.extra = extra;
        this.rawData = rawData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PushTargetEnum getPushTarget() {
        return pushTarget;
    }

    public void setPushTarget(PushTargetEnum pushTarget) {
        this.pushTarget = pushTarget;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Object getRawData() {
        return rawData;
    }

    public void setRawData(Object rawData) {
        this.rawData = rawData;
    }

    @Override
    public String toString() {
        return "推送平台:" + getPushTarget().brand + "\ntitle:" + getTitle() + "\ncontent:" + getContent();
    }
}
