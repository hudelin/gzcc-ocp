package com.hdl.gzccocpweb.security;

import com.hdl.gzccocpcore.properties.SecurityProperties;
import com.hdl.gzccocpcore.validate.code.ValidateCodeFilter;
import com.hdl.gzccocpweb.authentication.MyAuthenticationFailureHandler;
import com.hdl.gzccocpweb.authentication.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
//
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository=new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ValidateCodeFilter validateCodeFilter=new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();


        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/login")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getWebProperties().getRememberMeSeconds())
                .userDetailsService(myUserDetailsService)
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require", "/login", "/code/image",securityProperties.getWebProperties().getLoginPage()).permitAll()
                .antMatchers("/note/add").hasRole("USER")
                .anyRequest()
//                关闭登录验证
//                .permitAll()
//                开启登录验证
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
        web.ignoring().mvcMatchers("/res/**"); /*配置静态资源访问路径*/
        web.ignoring().antMatchers("/res/**");
    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }
}
