package com.example;

import com.example.domain.User;
import com.example.enums.SexEnum;
import com.example.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisPlusEnumTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        //INSERT INTO t_user ( user_name, age, sex ) VALUES ( ?, ?, ? )
        User user = new User();
        user.setName("lisi");
        user.setAge(22);
        user.setSex(SexEnum.MALE);
        int result = userMapper.insert(user);
        System.out.println(result);
    }
}
