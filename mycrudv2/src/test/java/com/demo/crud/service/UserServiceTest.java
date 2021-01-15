package com.demo.crud.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

/**
 * @Author: dyhu
 * @Date: 2019/6/13 17:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService service;

    @Test
    public void getUsersByPages() {
/*        List<User> list = service.getUsersByPages(1, 2);
        String result = list.toString();
        System.out.println(result);*/

    }
}