package com.bearever.push.model;

/**
 * 推送平台
 * Created by luoming on 2018/5/31.
 */

public enum PushTargetEnum {
    /**
     * 极光
     */
    JPUSH("JPUSH"),

    /**
     * 小米
     */
    XIAOMI("XIAOMI"),

    /**
     * 华为
     */
    HUAWEI("HUAWEI"),

    /**
     * OPPO
     */
    OPPO("OPPO"),

    /**
     * 魅族
     */
    MEIZU("MEIZU"),

    /**
     * VIVO
     */
    VIVO("VIVO");

    public String brand;

    PushTargetEnum(String brand) {
        this.brand = brand;
    }

}
