package com.ymd.user.utils;

import java.util.UUID;

public class KeyUtil {
    /**
     * 生成唯一的主键
     * 格式：UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
