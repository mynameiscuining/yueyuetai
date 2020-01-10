package cn.njyazheng.service.music;

import cn.njyazheng.configuration.FileProperties;
import cn.njyazheng.domain.music.Music;
import cn.njyazheng.mapper.music.MusicMapper;
import cn.njyazheng.util.Tools;
import cn.njyazheng.vo.OperatResult;
import cn.njyazheng.vo.PageResult;
import cn.njyazheng.vo.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class MusicService {
    @Autowired
    private MusicMapper musicMapper;
    @Autowired
    private FileProperties fileProperties;


    public PageResult<Music> getPageResult(Music music, Pagination pagination) {
        PageResult<Music> pageResult = new PageResult<>();
        pagination.setStart((pagination.getPage() - 1) * pagination.getLimit());
        pageResult.setCode(PageResult.NORMAL_CODE);
        pageResult.setCount(musicMapper.selectCountByCondition(music));
        pageResult.setData(musicMapper.selectByCondiction(music, pagination));
        return pageResult;
    }

    public Music getMusicByID(String id) {
        return musicMapper.selectByPrimaryKey(id);
    }

    public OperatResult addMusic(Music music, Part file) throws IOException {
        String id = Tools.generateKey(18);
        music.setId(id);
        String filename = file.getSubmittedFileName();
        music.setFilename(filename);
        String newname = filename.toLowerCase().endsWith(".mp3") ? id + ".mp3" : id + ".wav";
        String newpath = Tools.TimeUtil.now2String(Tools.TimeUtil.YYYYMMDD) + "/";
        music.setUri(fileProperties.getBaseUrl() + fileProperties.getMusicRoot() + newpath + newname);
        String musicpath = fileProperties.getBasePath() + fileProperties.getMusicRoot() + newpath;
        File filepath = new File(musicpath);
        if (!filepath.exists()) {
            filepath.mkdir();
        }
        file.write(musicpath + newname);
        int add = musicMapper.insert(music);
        if (add > 0) {
            return new OperatResult(OperatResult.SUCCESS, "添加成功!");
        } else {
            return new OperatResult(OperatResult.ERROR, "添加失败!");
        }

    }

    public OperatResult modify(Music music) {
        int modify = musicMapper.updateByPrimaryKeySelective(music);
        if (modify > 0) {
            return new OperatResult(OperatResult.SUCCESS, "修改成功!");
        } else {
            return new OperatResult(OperatResult.ERROR, "修改失败!");
        }
    }

    public OperatResult delete(Music music) {
        music = getMusicByID(music.getId());
        String path = fileProperties.getBasePath() + (music.getUri().replace(fileProperties.getBaseUrl(), ""));

        File file = new File(path);
        if (file.delete()) {
            int delete = musicMapper.deleteByPrimaryKey(music.getId());
            if (delete > 0) {
                return OperatResult.deleteSuccess();
            } else {
                return OperatResult.deleteFailure();
            }
        } else {
            return OperatResult.deleteFailure();
        }

    }
}
