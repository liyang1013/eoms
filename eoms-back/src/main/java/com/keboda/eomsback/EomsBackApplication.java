package com.keboda.eomsback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;

@SpringBootApplication
@MapperScan("com.keboda.eomsback.*.mapper")
@EnableTransactionManagement
public class EomsBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(EomsBackApplication.class, args);
    }

}
