package com.zhaodi.config.authentication;
import com.zhaodi.util.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author WangBo
 * @version 1.0
 * @Describe MD5加密类
 * @date 2020/4/8 16:06
 */
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

        /*System.out.println(MD5Util.encode((String)rawPassword)+"==="+encodedPassword+"==="+rawPassword);*/
        return encodedPassword.equalsIgnoreCase(MD5Util.encode((String)rawPassword));
    }

    /**
     * @Describe 对密码加密
     * @author WangBo
     */
    @Override
    public String encode(CharSequence rawPassword) {
        /*System.out.println(MD5Util.encode((String)rawPassword)+"==="+(String)rawPassword);*/
        return MD5Util.encode((String)rawPassword);
    }
}
