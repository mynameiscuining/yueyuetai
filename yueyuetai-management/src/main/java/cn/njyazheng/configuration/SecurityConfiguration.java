package cn.njyazheng.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        log.debug("ROOTadmin:" + bCryptPasswordEncoder.encode("ROOTadmin"));
        return bCryptPasswordEncoder;
    }

    /**
     * 静态资源
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/favicon.ico")
                .antMatchers("/css/**")
                .antMatchers("/image/**")
                .antMatchers("/skin/**")
                .antMatchers("/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 基础认证
        http.httpBasic();
        //表单登录
        /**
         * 1.successForwardUrl("/index")  登入成功后，跳转至指定页面
         * 2.defaultSuccessUrl("/index") 访问指定页面，用户未登入，
         * 跳转至登入页面，如果登入成功，跳转至用户访问指定页面，用户访问登入页面，默认的跳转页面
         */
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true);
        //注销
        http.logout().logoutUrl("/loginout")
                .logoutSuccessUrl("/");
        http.csrf().disable();
        http.headers().frameOptions().disable();
        //所有请求要认证
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/*/page/**", "/*/select/**", "/welcome", "/", "/error", "/loginout").authenticated()
                .anyRequest().access("@permissionService.hasPermission(request,authentication)");
    }


}

