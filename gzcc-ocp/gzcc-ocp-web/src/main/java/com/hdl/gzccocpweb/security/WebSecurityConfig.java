package com.hdl.gzccocpweb.security;

import com.hdl.gzccocpcore.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
//    @Autowired
//    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
//
//    @Autowired
//    private DbUserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/")
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require", "/", securityProperties.getWebProperties().getLoginPage()).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();

//                .antMatchers("/open/**","/","/community/**").permitAll()
//                .antMatchers("/administrator/**").hasRole("ADMINISTRATOR")
//                .antMatchers("/user/**").hasRole("USER")/*限制该路径格式为管理员身份可以访问*/
//
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")  /*设置登录界面为自定义登录界面*/
//                .successHandler(myAuthenticationSuccessHandler)
//                .failureHandler(myAuthenticationFailureHandler)
//                .successForwardUrl("/enter") /*身份正确后跳转路径*/
//                .failureForwardUrl("/reLogin")
//                .failureUrl("/login?error")
//                .failureForwardUrl("/reLogin")
//                .failureHandler(myAuthenticationFailureHandler)
//                .permitAll()

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().mvcMatchers("/res/**"); /*配置静态资源访问路径*/
        web.ignoring().antMatchers("/res/**");
    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }
}
