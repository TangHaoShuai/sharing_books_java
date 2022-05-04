package com.haoshuai.accountbook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.haoshuai.accountbook.mapper") //扫描mapper存放的地址
public class SharingBooksJava {

    public static void main(String[] args) {
        SpringApplication.run(SharingBooksJava.class, args);
    }

}
