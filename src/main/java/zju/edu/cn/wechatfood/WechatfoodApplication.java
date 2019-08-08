package zju.edu.cn.wechatfood;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@MapperScan(basePackages = "zju.edu.cn.wechatfood.dataobjcet.mapper")
@EnableCaching
public class WechatfoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatfoodApplication.class, args);
    }
 }
