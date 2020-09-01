package com.test;

import com.kll.springcloud.mapper.UserLoginMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
@Slf4j
public class TestDemo {

    @Test
    public void stat() {
        System.out.println(new Date().toString());
        System.out.println(UUID.randomUUID().toString());
    }
}
