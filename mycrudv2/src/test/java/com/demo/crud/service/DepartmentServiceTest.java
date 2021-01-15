package com.demo.crud.service;

import org.apache.tomcat.util.buf.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: dyhu
 * @Date: 2019/6/14 13:05
 */
public class DepartmentServiceTest {

    @Test
    public void getDeptlistByIds() {
        List<String> strs = new ArrayList<String>();
        strs.add("1");
        strs.add("2");
        strs.add("3");
        strs.add("4");
        strs.add("5");
        strs.add("6");
        String s = StringUtils.join(strs, ',');
        System.out.println(s);

    }
}