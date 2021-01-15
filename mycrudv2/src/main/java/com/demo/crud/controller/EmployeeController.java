package com.demo.crud.controller;

import com.demo.crud.dal.entity.Employee;
import com.demo.crud.domain.bo.EmployeeBO;
import com.demo.crud.service.EmployeeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: dyhu
 * @Date: 2019/6/14 18:31
 */
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/page/{pageNum}")
    public PageInfo<Employee> selectByPage(@PathVariable int pageNum) {
        return (employeeService.selectByPage(pageNum));
    }

}
