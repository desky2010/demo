package com.demo.crud.domain.biz;

import com.demo.crud.dal.criterion.DepartmentExample;
import com.demo.crud.dal.dao.DepartmentDao;
import com.demo.crud.dal.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: dyhu
 * @Date: 2019/6/15 14:41
 */
@Service
public class DepartmentBiz {
    @Autowired
    DepartmentDao deptDao;

    public Department getDeptById(Integer id) {
        if (id != null) {
            Department department = deptDao.selectByPrimaryKey(id);
            return department;
        } else {
            return null;
        }
    }

    public Map<Integer, Department> getDeptlistByIds(List<Integer> ids) {
        DepartmentExample example = new DepartmentExample();
        if (ids.size() == 0)  return null;
        example.createCriteria().andIdIn(ids);
        List<Department> departments = deptDao.selectByExample(example);
        Map<Integer, Department> depts = new HashMap<>();
        for (Department dept: departments
             ) {
            depts.put(dept.getId(), dept);
        }
        return depts;
    }
}
