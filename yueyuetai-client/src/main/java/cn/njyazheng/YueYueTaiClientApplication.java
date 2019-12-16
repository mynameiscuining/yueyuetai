package cn.njyazheng;

import com.ibeetl.starter.BeetlSqlSingleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {BeetlSqlSingleConfig.class})
public class YueYueTaiClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(YueYueTaiClientApplication.class, args);
    }
}
