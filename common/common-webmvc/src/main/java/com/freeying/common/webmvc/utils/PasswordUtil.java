package com.freeying.common.webmvc.utils;

import org.passay.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 密码工具类
 *
 * @author fx
 */
public final class PasswordUtil {
    private static final Logger log = LoggerFactory.getLogger(PasswordUtil.class);

    private static Random random = null;

    static {
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        }
    }

    private PasswordUtil() {
    }

    /**
     * 生成随机字母数字,可用于mfa
     *
     * @param targetStringLength 长度
     * @return String
     */
    public static String randomAlphanumeric(int targetStringLength) {
        // 数字 '0'
        int leftLimit = 48;
        // 字母 'z'
        int rightLimit = 122;

        return random.ints(leftLimit, rightLimit + 1)
                // 过滤掉 Unicode 65 和 90 之间的字符
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }


    /**
     * 生成强密码
     *
     * @return String
     */
    public static String buildDefaultPassword() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String password = passwordGenerator.generatePassword(8,
                // 至少有一个大写字母
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // 至少有一个小写字母
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // 至少有一个数字
                new CharacterRule(EnglishCharacterData.Digit, 1),
                // 至少有一个特殊字符
                new CharacterRule(EnglishCharacterData.Special, 1));
        if (!validatePassword(password)) {
            buildDefaultPassword();
        }
        return password;
    }

    /**
     * 校验密码强度
     *
     * @param password password
     * @return boolean
     */
    public static boolean validatePassword(String password) {
        PasswordValidator validator = new PasswordValidator(getPasswordRules());
        RuleResult result = validator.validate(new PasswordData(password));
        return result.isValid();
    }

    /**
     * 强密码规则
     *
     * @return List
     */
    public static List<Rule> getPasswordRules() {
        return Arrays.asList(
                // 长度规则：8 - 30 位
                new LengthRule(8, 30),
                // 至少有一个大写字母
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // 至少有一个小写字母
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // 至少有一个数字
                new CharacterRule(EnglishCharacterData.Digit, 1),
                // 至少有一个特殊字符
                new CharacterRule(EnglishCharacterData.Special, 1),
                // 不允许连续 3 个字母，按字母表顺序
                // alphabetical is of the form 'abcde', numerical is '34567', qwery is 'asdfg'
                // the false parameter indicates that wrapped sequences are allowed; e.g. 'xyzabc'
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
                // 不允许 3 个连续数字
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false),
                // 不允许 QWERTY 键盘上的三个连续相邻的按键所代表的字符
                new IllegalSequenceRule(EnglishSequenceData.USQwerty, 5, false),
                // 不允许包含空格
                new WhitespaceRule());
    }

}
