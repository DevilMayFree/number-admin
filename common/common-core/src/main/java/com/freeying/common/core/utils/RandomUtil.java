package com.freeying.common.core.utils;

import java.util.Random;

public final class RandomUtil {

    private static final Random random = new Random();

    private RandomUtil() {
    }

    /**
     * 生成随机字符串
     *
     * @param length 长度
     * @return 随机字符串
     */
    @SuppressWarnings("squid:S125")
    public static String getStringRandom(int length) {
        StringBuilder val = new StringBuilder();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                // int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + 65));
            } else {
                val.append(random.nextInt(10));
            }
        }
        return val.toString();
    }
}
