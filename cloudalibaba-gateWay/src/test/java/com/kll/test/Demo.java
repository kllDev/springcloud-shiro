package com.kll.test;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Demo {
    @Test
    public void test() {
        String s = "PREFIX_USER_TOKEN_test";
        System.out.println(s.substring(0,18));
    }
}
