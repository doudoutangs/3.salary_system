package com.salary.util;

import com.alibaba.fastjson.JSON;
import com.salary.contants.ExceptionEnum;
import com.salary.exception.BizException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * 工具类
 *
 * @author sun
 */
public class ToolUtil {

    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }

        return sb.toString();
    }

    public static void renderJson(HttpServletResponse response, Object jsonObject) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(jsonObject));
        } catch (IOException e) {
            throw new BizException(ExceptionEnum.SYSTEM_ERRO.getCode(), ExceptionEnum.SYSTEM_ERRO.getMessage());
        }
    }
}
