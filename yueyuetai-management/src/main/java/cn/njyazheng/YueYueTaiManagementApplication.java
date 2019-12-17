package cn.njyazheng;

import com.ibeetl.starter.BeetlSqlSingleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {BeetlSqlSingleConfig.class})
public class YueYueTaiManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(YueYueTaiManagementApplication.class, args);
    }
}
