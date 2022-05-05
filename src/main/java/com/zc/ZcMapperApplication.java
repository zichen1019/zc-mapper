package com.zc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author <a href="https://github.com/zichen1019">zichen</a>
 */
@MapperScan(basePackages = "com.zc")
@SpringBootApplication
public class ZcMapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZcMapperApplication.class, args);
    }

}
