package com.demo.crud.domain.bo;

import com.demo.crud.dal.entity.Employee;

/**
 * @Author: dyhu
 * @Date: 2019/6/15 14:37
 */
public class EmployeeBO {
    private Employee employee;
    private String departName;

    public String getGenderName() {
        if (employee != null && employee.getGender() == 1) {
            return "男";
        } else {
            return "女";
        }
    }


    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }
}
