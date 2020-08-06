package com.example.springsecurity.config;

import com.example.springsecurity.bean.VerificationAuthenticationFailureHandler;
import com.example.springsecurity.common.exception.AccountLockedException;
import com.example.springsecurity.enumeration.Authority;
import com.example.springsecurity.model.Role;
import com.example.springsecurity.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Objects;

/**
 * @author ClowLAY
 * create date 2020/5/23
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER= LoggerFactory.getLogger(UserSecurityConfig.class);


    private UserRepository userRepository;

    private ObjectMapper objectMapper;

    private VerificationAuthenticationFailureHandler verificationAuthenticationFailureHandler;




    @Autowired
    public UserSecurityConfig(UserRepository userRepository, ObjectMapper objectMapper,
                              VerificationAuthenticationFailureHandler verificationAuthenticationFailureHandler) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.verificationAuthenticationFailureHandler = verificationAuthenticationFailureHandler;
    }





    /**
     * 指定密码 加密与效验
     */

    @Bean
    public PasswordEncoder md5PasswordEncoder(){

        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return DigestUtils.md5Hex(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(encode(rawPassword));
            }
        };
    }

    /**
     * # 解决UsernameNotFoundException不抛出
     *  原因：查看源码：
     *      org/springframework/security/authentication/ProviderManager.java 175行
     *      org/springframework/security/authentication/dao/DaoAuthenticationProvider.java 116行
     *      org/springframework/security/authentication/dao/AbstractUserDetailsAuthenticationProvider.java 150行
     *
     *          hideUserNotFoundExceptions参数 默认值为true 故UsernameNotFoundException被包装成 BadCredentialsException抛出
     *   1.注入DaoAuthenticationProvider
     *   通过调用setHideUserNotFoundExceptions 修改默认值  setUserDetailsService  setPasswordEncoder
     *   2. 在 configure(AuthenticationManagerBuilder auth) 中配置 auth.authenticationProvider(authenticationProvider());即可
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(md5PasswordEncoder());
        return provider;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
       auth.authenticationProvider(authenticationProvider());

    }

    /**
     * 重写 UserDetailsService 定义校验规则
     * @return
     */

    @Override
    protected UserDetailsService userDetailsService(){
        return (username) ->{
            //从数据中取出用户信息
            var user = userRepository.findByUsername(username).orElse(null);
            //判断用户是否存在
            if (user == null){
                    throw new UsernameNotFoundException("用户["+username+"]不存在");

            }
            if (user.getLock()){
                throw new AccountLockedException("账号已锁定，请联系管理员解锁！");
            }
            return User.withUsername(username)//添加用户名
                    .password(user.getPassword())//添加密码
                    //添加用户权限
                    .authorities(user.getRoles().stream()
                            .filter(Objects::nonNull)
                            .map(Role::getAuthorities)
                            .flatMap(Collection::stream)
                            .map(Authority::toString)
                            .map(SimpleGrantedAuthority::new)
                            .toArray(SimpleGrantedAuthority[]::new)).build();

        };
    }


    @Override
    protected void configure(HttpSecurity http) throws  Exception{

        http.headers()
                .frameOptions()
                .disable();
        http.authorizeRequests()
                //设置拦截，对以下资源放行
                .antMatchers("/login","/public/**")
                .permitAll()
                .anyRequest()
                //登录必须权限
                .hasAnyAuthority("WAM_USER");
        //SpringSecurity保护机制
         http.csrf().disable();
        http.formLogin()
                //设置登录页
                .loginPage("/login")
                //设置登录处理接口
                .loginProcessingUrl("/api/v1/login")
                .permitAll()
                .defaultSuccessUrl("/")
                //设置登录成功处理方法
        .successHandler(authenticationSuccessHandler())
                //登录错误处理界面
        .failureHandler(verificationAuthenticationFailureHandler);

    }



    private  AuthenticationSuccessHandler authenticationSuccessHandler(){

        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication )->{
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            var root =objectMapper.createObjectNode();
            root.put("redirect",
                  request.getRequestURI().equals("/api/v1/login")?"/":request.getRequestURI());
            response.getOutputStream().write(root.toString().getBytes());
        };
    }



}
