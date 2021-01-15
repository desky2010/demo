package com.demo.crud.service;

import com.demo.crud.dal.dao.EmployeeDao;
import com.demo.crud.dal.entity.Employee;
import com.demo.crud.dal.criterion.EmployeeExample;
import com.demo.crud.domain.biz.EmployeeBiz;
import com.demo.crud.domain.bo.EmployeeBO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: dyhu
 * @Date: 2019/6/14 11:59
 */
@Service
public class EmployeeService {
    @Autowired(required = false)
    EmployeeDao employeeDao;

    @Autowired
    EmployeeBiz employeeBiz;

    //部门列表查询
    public PageInfo<EmployeeBO> selectByPage(int pageNum, int pageSize) {
        //Example、Dao，包括selectByExample方法都是代码生成器生成
        EmployeeExample example = new EmployeeExample();
        example.setOrderByClause("id desc");
        PageHelper.startPage(pageNum, pageSize);
        //selectByExample(example)，因为没有指定任何查询条件，正常是返回所有数据
        //在这里指定PageHelper拦截了sql，加上了limit参数
        List<Employee> emps = employeeDao.selectByExample(example);
        //需要更新部门
        List<EmployeeBO> empBOs = employeeBiz.getEmpBOsByPO(emps);
        //如果直接返回以上list，得到了分页的数据
        // 如果添加下面步骤，则返回pageInfo，则能得到包括list在内的分页信息
        PageInfo<EmployeeBO> pageInfo = new PageInfo<>(empBOs);
        return pageInfo;
    }

    //部门列表查询
    public PageInfo<Employee> selectByPage(int pageNum) {
        //Example、Dao，包括selectByExample方法都是代码生成器生成
        EmployeeExample example = new EmployeeExample();
        example.setOrderByClause("id desc");
        PageHelper.startPage(pageNum, 20);
        //selectByExample(example)，因为没有指定任何查询条件，正常是返回所有数据
        //在这里指定PageHelper拦截了sql，加上了limit参数
        List<Employee> emps = employeeDao.selectByExample(example);
        //如果直接返回以上list，得到了分页的数据
        // 如果添加下面步骤，则返回pageInfo，则能得到包括list在内的分页信息
        PageInfo<Employee> pageInfo = new PageInfo<>(emps);
        return pageInfo;
    }

    public Employee selectEmpById(Integer id) {
        if (id == null) return null;

        Employee employee = employeeDao.selectByPrimaryKey(id);
        //部门？
        return employee;
    }

    public void saveEmp(Employee employee) {
        if (employee == null) return;
        if (employee.getdId() == null) {
            //新增
            employeeDao.insert(employee);
        } else {
            //修改
            employeeDao.updateByPrimaryKey(employee);
        }
    }

    public void deleteEmp(Integer id) {
        if (id == null) return;
        employeeDao.deleteByPrimaryKey(id);
    }

}
