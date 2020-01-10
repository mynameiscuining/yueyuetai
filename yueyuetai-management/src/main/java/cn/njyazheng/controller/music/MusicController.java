package cn.njyazheng.controller.music;

import cn.njyazheng.domain.music.Music;
import cn.njyazheng.service.music.MusicService;
import cn.njyazheng.vo.OperatResult;
import cn.njyazheng.vo.PageResult;
import cn.njyazheng.vo.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Part;
import java.io.IOException;

@RestController
@RequestMapping("/music")
@Slf4j
public class MusicController {
    @Autowired
    private MusicService musicService;

    @GetMapping("/list")
    public PageResult<Music> getList(Music music, Pagination pagination) {
        return musicService.getPageResult(music, pagination);
    }

    @PostMapping("/add")
    public OperatResult add(Music music, Part file) throws IOException {
        return musicService.addMusic(music, file);
    }

    @PostMapping("/modify")
    public OperatResult modify(Music music) {
        return musicService.modify(music);
    }

    @PostMapping("/delete")
    public OperatResult delete(Music music) {
        return musicService.delete(music);
    }
}
