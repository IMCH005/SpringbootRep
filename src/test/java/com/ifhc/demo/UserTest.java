package com.ifhc.demo;

import com.ifhc.entity.User;
import com.ifhc.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
     UserMapper userMapper;

    @Test
    public void test(){
        User user=userMapper.getById(1);
        System.out.println(user);
    }
}
