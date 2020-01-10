package cn.njyazheng.controller.music;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

public class MusicViewerRegistry {

    public static void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/music").setViewName("music/music.html");
    }
}
