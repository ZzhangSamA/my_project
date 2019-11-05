package com.yanzhang.utils;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.util.DigestUtils;

/**
 * 密码加盐处理
 */
public class CredentialUtils {

    /**
     * 自定义异常
     */
    public class SaltInvalidNumberException extends RuntimeException {
        public SaltInvalidNumberException(String msg) {
            super(msg);
        }
    }

    /**
     *  20 2c b9 62 ac 59 075b964b07152d234b70
     *  将密码md5加密后，长度为32， 再生成16个随机字符串,每两位中间插一个字符。
     *  形成了48位长度的字符串，在做md5加密。
     * @param password
     * @return
     */
    public static String cryptPassword(String password, String saltValue) {
        char[] pwdMd5 = DigestUtils.md5DigestAsHex(password.getBytes()).toCharArray();
        char[] saltCharArr = saltValue.toCharArray();

        if (saltCharArr.length != 16) {
            throw new CredentialUtils().new SaltInvalidNumberException("盐值长度必须为16");
        }

        char[] newPwdArray = new char[48];
        for (int i = 0; i < 16; i++) {
            newPwdArray[i*3] = pwdMd5[i*2];
            newPwdArray[i*3 + 1] = pwdMd5[i*2 + 1];
            newPwdArray[i*3 + 2] = saltCharArr[i];
        }

        return DigestUtils.md5DigestAsHex(new String(newPwdArray).getBytes());
    }

    public static void main(String[] args) {
//        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('a','z').build();
//        String randomString = generator.generate(16);

        String salt = "kbaaetyxrkhprdby";
        String password = cryptPassword("123",salt);
        System.out.println(salt);
        System.out.println(password);
    }
}
