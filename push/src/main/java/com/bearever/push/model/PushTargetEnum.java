package com.bearever.push.model;

/**
 * 推送平台
 * Created by luoming on 2018/5/31.
 */

public enum PushTargetEnum {
    JPUSH("JPUSH"), //极光

    XIAOMI("XIAOMI"), //小米

    HUAWEI("HUAWEI"),//华为

    OPPO("OPPO"),//oppo

    MEIZU("MEIZU"); //魅族

    public String brand;

    PushTargetEnum(String brand) {
        this.brand = brand;
    }

}
