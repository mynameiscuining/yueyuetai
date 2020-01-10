package cn.njyazheng.configuration;

import cn.njyazheng.util.Tools;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "filepath")
public class FileProperties {
    private String basePath = "/";
    private String baseUrl = "http://localhsot:7400/file/";
    private String musicRoot = "";
    private String staticRoot = "";
    private String imageRoot="";
    
    public String getBasePath() {
        return Tools.FileUtil.completionPath(basePath);
    }
    
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
    
    public String getBaseUrl() {
        return Tools.URIUtil.completionURI(baseUrl);
    }
    
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    
    public String getMusicRoot() {
        return Tools.FileUtil.completionPath(musicRoot);
    }
    
    public void setMusicRoot(String musicRoot) {
        this.musicRoot = musicRoot;
    }
    
    public String getStaticRoot() {
        return Tools.FileUtil.completionPath(staticRoot);
    }
    
    public void setStaticRoot(String staticRoot) {
        this.staticRoot = staticRoot;
    }
    
    public String getImageRoot() {
        return Tools.FileUtil.completionPath(imageRoot);
    }
    
    public void setImageRoot(String imageRoot) {
        this.imageRoot = imageRoot;
    }
}
