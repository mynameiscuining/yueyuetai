package cn.njyazheng.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

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
                .antMatchers("/lib/**")
                .antMatchers("/image/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 基础认证
        http.httpBasic();
        //表单登录
        http.formLogin()
                .loginPage("/index.html")
                .loginProcessingUrl("/")
                .defaultSuccessUrl("/");
        //注销
        http.logout().logoutUrl("/loginout")
                .logoutSuccessUrl("/");

        //所有请求要认证
        http.authorizeRequests()
                //不需要认证,比如静态资源
                .antMatchers("/**").permitAll()
                .antMatchers("/like", "/newtag", "addmusic").authenticated();
    }
}

