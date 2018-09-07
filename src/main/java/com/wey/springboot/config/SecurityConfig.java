package com.wey.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法级别的权限认证
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private UserDetailsService userDetailService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.authorizeRequests().anyRequest().authenticated() // 任何请求,登录后可以访问
                .and().formLogin().loginPage("/login").failureUrl("/login?error").permitAll() // 登录页面用户任意访问
                .and().logout().permitAll(); // 注销行为任意访问*/
        
        /*http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login.html")
                .defaultSuccessUrl("/index.html").failureForwardUrl("/login.html?error").permitAll().and().rememberMe()
                .tokenValiditySeconds(1209600).key("TOKEN").and().logout().logoutUrl("/logout")
                .logoutSuccessUrl("/logout-success");*/
        
        http.authorizeRequests().antMatchers("/index", "/login.html", "/css/**", "/js/**").permitAll() // 都可以访问
                .anyRequest().authenticated().antMatchers("/users/**").hasRole("ADMIN").and().formLogin() // 基于FORM表单的验证
                
                .loginPage("/login.html").failureUrl("/login-error.html").defaultSuccessUrl("/home/index").and()
                .rememberMe().tokenValiditySeconds(1209600).key("TOKEN").and().logout().logoutUrl("/logout")
                .logoutSuccessUrl("/logout-success");
    }
    
    /**
     * 自定义认证策略
     */
    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // auth.authenticationProvider(authProvider()).eraseCredentials(true);
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
        
        System.out.println(passwordEncoder().encode("123456"));
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
