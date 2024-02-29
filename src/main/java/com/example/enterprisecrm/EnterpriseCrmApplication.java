package com.example.enterprisecrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.enterprisecrm.mapper")
public class EnterpriseCrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnterpriseCrmApplication.class, args);
    }

}
