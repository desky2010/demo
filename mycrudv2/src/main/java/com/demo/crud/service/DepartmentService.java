package com.demo.crud.service;

import com.demo.crud.dal.dao.DepartmentDao;
import com.demo.crud.dal.entity.Department;
import com.demo.crud.dal.criterion.DepartmentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * @Author: dyhu
 * @Date: 2019/6/14 11:55
 */
@Service
public class DepartmentService {
    @Autowired
    DepartmentDao departmentDao;

    public List<Department> getAllDepts() {
        DepartmentExample example = new DepartmentExample();
        List<Department> departments = departmentDao.selectByExample(example);
        return departments;
    }


}
