package cn.njyazheng.controller.system;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

public class SysViewerRegistry {

    public static void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/userinfo").setViewName("organization/user.html");
        registry.addViewController("userinfo/page/add").setViewName("organization/user-add.html");
        registry.addViewController("/role").setViewName("organization/role.html");
        registry.addViewController("/role/page/grant").setViewName("organization/role-grant.html");
        registry.addViewController("/userinfo/page/grant").setViewName("organization/user-grant.html");
        registry.addViewController("/dictionary").setViewName("setting/dictionary.html");
    }
}
