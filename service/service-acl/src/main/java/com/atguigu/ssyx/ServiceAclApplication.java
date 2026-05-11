package com.atguigu.ssyx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.atguigu.ssyx.*.mapper")
public class ServiceAclApplication {

    public static void main(String[] args){
        SpringApplication.run(ServiceAclApplication.class,args);
    }
}
