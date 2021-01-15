package com.demo.crud.service;

import com.demo.crud.dal.criterion.UserExample;
import com.demo.crud.dal.dao.UserDao;
import com.demo.crud.dal.entity.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * @Author: dyhu
 * @Date: 2019/6/13 16:44
 */
@Service
public class UserService {

    @Autowired(required = false)
    private UserDao userDao;

     public boolean doLogin(User user) {
        if (user == null) return false;

         UserExample example = new UserExample();
         example.createCriteria()
                 .andLoginEqualTo(user.getLogin())
                 .andPasswordEqualTo(user.getPassword());

         List<User> users = userDao.selectByExample(example);

        if (users == null || users.size() <= 0) return false;
        return true;
    }

    //分页查询用户列表
    public PageInfo<User> selectByPage(int pageNum, int pageSize) {
        UserExample userExample = new UserExample();
        userExample.setOrderByClause("id desc");
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userDao.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return pageInfo;
    }

    public void saveUser(User user) {
        if (user == null) return;
        if (user.getId() == null) {
            userDao.insert(user);
        } else {
            userDao.updateByPrimaryKey(user);
        }
    }

    public void deleteUser(Integer id) {
        if(id == null) return;
        userDao.deleteByPrimaryKey(id);
    }

    public User getUserById(Integer id) {
        if (id == null) return null;
        User user = userDao.selectByPrimaryKey(id);
        return user;
    }
}
