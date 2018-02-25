package com.panly.urm.common;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.UUID;

/**
 * <p>
 * 随机字符工具类
 * </p>
 *
 * @author a@panly.me
 * @project core
 * @class RandomUtil
 * @date 2017年8月17日下午2:23:58
 */
public class RandomCharUtil {

    public final static String[] NUM_ARRAY = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

    public final static String[] CHAR_ARRAY = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", };

    public final static String[] ALL_ARRAY = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
            "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z", };

    /**
     * 生产len长度随机数字串
     *
     * @param header 开头
     * @param length 长度
     * @return 长度为len的数字串
     */
    public static String randomNum(String header, int length) {
        return (StringUtils.isBlank(header) ? "ENN" : header) + randomNum(length);
    }

    /**
     * 生产len长度随机数字串
     *
     * @param length 长度
     * @return 长度为len的数字串
     */
    public static String randomNum(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(NUM_ARRAY[random.nextInt(NUM_ARRAY.length)]);
        }
        return sb.toString();
    }

    /**
     * 生产len长度不含数字随机字符串
     *
     * @param length 长度
     * @return 长度为len的不含数字随机字符串
     */
    public static String randomLetter(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(CHAR_ARRAY[random.nextInt(CHAR_ARRAY.length)]);
        }
        return sb.toString();
    }

    /**
     * 生成len长度随机字符串
     *
     * @param length 随机字符串长度
     * @return 长度为len的随机字符串
     */
    public static String random(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_ARRAY[random.nextInt(ALL_ARRAY.length)]);
        }
        return sb.toString();
    }

    /**
     * 生成len长度随机字符串
     *
     * @param len 随机字符串长度
     * @return 长度为len的随机字符串
     */
    public static String randomShortUuid(int len) {
        StringBuilder builder = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < len; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int strInteger = Integer.parseInt(str, 16);
            builder.append(ALL_ARRAY[strInteger % 0x3E]);
        }

        return builder.toString();
    }

}
