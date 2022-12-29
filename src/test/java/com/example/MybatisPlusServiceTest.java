package com.example;


import com.example.domain.User;
import com.example.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisPlusServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetCount() {
        //SELECT COUNT( * ) FROM user
        long count = userService.count();
        System.out.println("总记录数" + count);
    }


    @Test
    public void testSaveBatch() {
        //INSERT INTO user ( id, name, age ) VALUES ( ?, ?, ? )
        List<User> list = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            User user = new User();
            user.setName("yyb" + i);
            user.setAge(20 + i);
            user.setEmail("yyb" + i + "@qq.com");
            list.add(user);
        }
        boolean b = userService.saveBatch(list);
        System.out.println(b);
    }
}
