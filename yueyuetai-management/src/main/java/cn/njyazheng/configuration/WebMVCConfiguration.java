package cn.njyazheng.configuration;

import cn.njyazheng.controller.music.MusicViewerRegistry;
import cn.njyazheng.controller.system.SysViewerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfiguration implements WebMvcConfigurer {
    @Autowired
    private ServerProperties serverProperties;
    @Autowired
    private PermissionInterceptor permissionInterceptor;

    @Bean
    public ErrorController errorController(ErrorAttributes errorAttributes) {
        return new ErrorPageController(errorAttributes, this.serverProperties.getError());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login.html");
        registry.addViewController("/welcome").setViewName("welcome.html");
        SysViewerRegistry.addViewControllers(registry);
        MusicViewerRegistry.addViewControllers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionInterceptor).addPathPatterns("/{*:[A-Za-z]+$}");
    }
}
