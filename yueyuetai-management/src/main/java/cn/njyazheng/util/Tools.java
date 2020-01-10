package cn.njyazheng.util;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Tools {
    public static synchronized String generateKey(int count){
        if(count<=14){
            throw  new IllegalArgumentException("param required count >14");
        }
       return TimeUtil.now2String(TimeUtil.YYYYMMDDHHMMSS) + RandomStringUtils.randomAlphabetic(count-14);
    }
    
    public static class TimeUtil {
        public static final String YYYY_MM_DD_HH$MM$SS = "yyyy-MM-dd HH:mm:ss";
        public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
        public static final String YYYYMMDD = "yyyyMMdd";
        
        public static String date2String(Date date, String formate) {
            LocalDateTime localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formate);
            return localDate.format(dateTimeFormatter);
        }
        
        public static String now2String(String formate) {
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formate);
            return localDateTime.format(dateTimeFormatter);
        }
    }
    
    public static class FileUtil {
        public static String completionPath(String path) {
            if (StringUtils.isNotBlank(path)) {
                path = path.endsWith("/") || path.endsWith("\\") ? path : path + "/";
            }
            return path;
        }
    }
    
    public static class URIUtil{
        public static String completionURI(String url) {
            if (StringUtils.isNotBlank(url)) {
                url = url.endsWith("/") ? url : url + "/";
            }
            return url;
        }
    }
    
}
