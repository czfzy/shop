package com.example.tool;

import cn.hutool.core.date.DateTime;

public class DateTimeUtil {
    //转换时间戳
    public static int toTimestamp(DateTime date) {
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.valueOf(timestamp);
    }
}
