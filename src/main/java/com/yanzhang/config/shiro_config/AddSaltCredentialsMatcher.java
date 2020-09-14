package com.yanzhang.config.shiro_config;

import com.yanzhang.utils.CredentialUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.util.ByteSource;

public class AddSaltCredentialsMatcher extends CodecSupport implements CredentialsMatcher {

    /**
     * token 是用户在登录的时候，构建UsernameAndPasswordToken 其中封装了 name和password
     * info 是CustomerRealm中 doGetAuthenticationInfo 中方法的返回值。
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        Object tokenCredentials = token.getCredentials();
        Object infoCredentials = info.getCredentials();

        if(info instanceof SimpleAuthenticationInfo) {
            SimpleAuthenticationInfo simpleAuthenticationInfo = (SimpleAuthenticationInfo)info; //向下类型转换

            ByteSource credentialsSalt = simpleAuthenticationInfo.getCredentialsSalt();

            // isByteSource是判断类型是否属于 byte[]  或者 char[] 或者 String
            if (isByteSource(credentialsSalt) && isByteSource(infoCredentials) && isByteSource(tokenCredentials)) {
                /**
                 * 通过这种方式获取到是base64编码后的数据
                 * ByteSource salt = simpleAuthenticationInfo.getCredentialsSalt();
                 */
                byte[] salt = toBytes(credentialsSalt);  //获取盐值
                byte[] tokenBytes = toBytes(tokenCredentials);   //获取到token，也就是用户提交过来的密码
                byte[] accountBytes = toBytes(infoCredentials);  //获取到数据库密码

                //加盐处理后的密码
                String newPwd = CredentialUtils.cryptPassword(new String(tokenBytes), new String(salt));

                System.out.println(new String(salt) + "=====" + new String(tokenBytes) + " === 数据库密码：" + newPwd);

                return newPwd.equals(new String(accountBytes));
            }
        }
        return false;
    }
}
