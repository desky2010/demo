package com.demo.crud.domain.biz;

import com.demo.crud.dal.entity.Department;
import com.demo.crud.dal.entity.Employee;
import com.demo.crud.domain.bo.EmployeeBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: dyhu
 * @Date: 2019/6/15 14:41
 */
@Service
public class EmployeeBiz {
    @Autowired
    DepartmentBiz departmentBiz;

    public List<EmployeeBO> getEmpBOsByPO(List<Employee> emps) {
        if (emps == null || emps.size() == 0) return null;
        List<Integer> ids = new ArrayList<>();
        for (Employee emp : emps
             ) {
            ids.add(emp.getdId());
        }

        Map<Integer, Department> deptlist = departmentBiz.getDeptlistByIds(ids);

        EmployeeBO bo;
        List<EmployeeBO> bos = new ArrayList<>();
        for (Employee emp : emps
             ) {
            if (deptlist.containsKey(emp.getdId())) {
                bo = new EmployeeBO();
                bo.setDepartName(deptlist.get(emp.getdId()).getDepartmentname());
                bo.setEmployee(emp);
                bos.add(bo);
            }
        }
        return bos;
    }

}
