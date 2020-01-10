package cn.njyazheng.controller.music;

import cn.njyazheng.domain.music.Music;
import cn.njyazheng.service.music.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MusicPageViewController {
    @Autowired
    private MusicService musicService;

    @GetMapping({"/music/page/add", "/music/page/add/{id}"})
    public String toMusicPage(@PathVariable(value = "id", required = false) String id, ModelMap modelMap) {
        if (StringUtils.isEmpty(id)) {
            modelMap.put("action", "add");
            modelMap.put("music", new Music());
        } else {
            modelMap.put("action", "modify");
            modelMap.put("music", musicService.getMusicByID(id));
        }
        return "/music/music-add.html";
    }
}
