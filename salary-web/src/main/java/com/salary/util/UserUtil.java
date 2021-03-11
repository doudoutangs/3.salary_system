package com.salary.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by sugar on 2020/5/4.
 */
public class UserUtil {

    public static String getUserNo(int id) {
        String no = StringUtils.leftPad(String.valueOf(id), 4, "0");
        return no;
    }
}
