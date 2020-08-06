package com.example.springsecurity.common.bean;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *  全局常量
 * @author tanchong
 * Create Date: 2020/3/5
 */
public class GlobalConstant {

    /**
     * session中图形验证码的key
     */
    public static final String VALIDATE_CODE_SESSION_KEY = "SVC";

    /**
     * requset请求中图形验证码的key
     */
    public static final String VALIDATE_CODE_REQUEST_KEY = "svc";

    /**
     * 用户名、密码错误次数
     */
    public static final int NUMBER_OF_USERNAME_AND_PASSWORD_ERRORS = 5;

    /**
     * 注册验证码邮件标题
     */
    public static final String REGISTER_SUBJECT = "WAM(网安资产管理系统)注册验证码";

    /**
     *  用户锁定邮件标题
     */
    public static final String ACCOUNT_SUBJECT = "WAM(网安资产管理系统)用户锁定";

    /**
     *  管理员账户
     */
    public static final String ADMINISTRATOR = "root";

    /**
     *  管理员账户角色
     */
    public static final String ADMINISTRATOR_ROLE = "root";

    /**
     *  普通账户角色
     */
    public static final String DOMESTIC_CONSUMER_ROLE = "domestic_consumer";

    public static AtomicInteger atomicInteger = new AtomicInteger(0);

}
