package com.example.springsecurity.bean;


import com.example.springsecurity.common.bean.ErrorResponse;
import com.example.springsecurity.common.bean.GlobalConstant;
import com.example.springsecurity.common.exception.AccountLockedException;
import com.example.springsecurity.common.exception.ValidateCodeException;
import com.example.springsecurity.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 校验失败全局处理器
 * @author TanChong
 * create date 2020\3\3 0003
 */
@Component
public class VerificationAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationAuthenticationFailureHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;



//    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json; charset=UTF-8");
        var errorResponse = new ErrorResponse(exception.getMessage());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        if (exception instanceof ValidateCodeException) {
            errorResponse.setError(exception.getMessage());
        }else if (exception instanceof UsernameNotFoundException) {
            errorResponse.setError(exception.getMessage());
        } else if (exception instanceof BadCredentialsException) {
            var username = request.getParameter("username");
            //错误次数加1
            GlobalConstant.atomicInteger.addAndGet(1);
            if (GlobalConstant.NUMBER_OF_USERNAME_AND_PASSWORD_ERRORS-GlobalConstant.atomicInteger.get() == 0){
               var user = userRepository.findByUsername(username).orElse(null);
                errorResponse.setError("您的账户["+username+"]因登录错误次数过多,导致账户被锁定,请联系管理员解锁,或等待账号解锁.");
               user.setLock(true);//锁定用户
               userRepository.save(user);
                GlobalConstant.atomicInteger = new AtomicInteger(0);
            }else{
                errorResponse.setError("用户名密码输入错误,请重新输入,您还有" + (GlobalConstant.NUMBER_OF_USERNAME_AND_PASSWORD_ERRORS - GlobalConstant.atomicInteger.get()) + "次重试机会!");
            }




        } else if (exception instanceof AccountLockedException) {
            errorResponse.setError(exception.getMessage());
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorResponse.setError(exception.getMessage());
        } else if (exception instanceof CookieTheftException) {
            LOGGER.info("系统检测到无效的(序列/标记),意味着先前的cookie可能被盗窃攻击,请重新登录！");
            errorResponse.setError("系统检测到无效的(序列/标记),意味着先前的cookie可能被盗窃攻击,请重新登录！");
        }
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.getOutputStream().write(objectMapper.writeValueAsBytes(errorResponse));
    }
    @Bean
    public VerificationAuthenticationFailureHandler getVerificationAuthenticationFailureHandler() {
        return new VerificationAuthenticationFailureHandler();
    }

}
