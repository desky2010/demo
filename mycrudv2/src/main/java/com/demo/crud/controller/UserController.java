package com.demo.crud.controller;

import com.demo.crud.dal.entity.User;
import com.demo.crud.service.UserService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Map;


/**
 * @Author: dyhu
 * @Date: 2019/6/13 17:27
 */
@Controller
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @Value("${default-page-size}")
    private Integer pageSize;

    @PostMapping("/login/check")
    public String doLogin(@RequestParam("userName") String userName,
                          @RequestParam("password") String password,
                          Map<String, Object> result) {

        if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)) {
            User user = new User();
            user.setLogin(userName);
            user.setPassword(password);

            if (userService.doLogin(user)) {
                return "redirect:/users/1";
            }
        }
        result.put("errmsg", "用户名或者密码错误");
        return "login";
    }

    @GetMapping("/users/{page}")
    public String selectUsers(@PathVariable("page") Integer page,
                              Model model) {
        logger.info("page: " + page);

        if (page == null) page = 1;
        PageInfo<User> pageInfo = userService.selectByPage(page, pageSize);
        model.addAttribute("users", pageInfo);

        if (pageInfo.getStartRow() > pageInfo.getTotal() ||
                pageInfo.getPages() < page) {
            if (pageInfo.getPages() > 1)
                page = pageInfo.getPages() ;
            else
                page = 1;
            return "redirect:/users/" + page;
        }

        //必须要和templates中的html对应
        return "userlist";
    }

    //ajax请求
    @GetMapping("user/{id}")
    @ResponseBody
    public User getUser(@PathVariable("id") Integer id) {
        if (id == null) return null;
        User user = userService.getUserById(id);
        return user;
    }

    //删除，用thymeleaf后台输出的同步方式（非ajax）
    @DeleteMapping("/user/{id}/page/{currentpage}")
    public String deleteUser(@PathVariable("id") Integer id,
                             @PathVariable("currentpage") Integer page) {
        if (page == null) page = 1;
        if (id != null) {
            userService.deleteUser(id);
            return "redirect:/users/" + page;
        }
        return "userlist";
    }

    //修改保存（非ajax）
    @PutMapping("/user/page/{currentpage}")
    public String updateUser(@PathVariable("currentpage") Integer page, User user) {
        if (page == null) page = 1;
        if (user != null) {
            userService.saveUser(user);

            logger.warn("update is ok");
            return "redirect:/users/" + page;
        }
        return "userlist";
    }

    //添加用户
    @PostMapping("user")
    public String addUser(User user) {
        if (user != null) {
            userService.saveUser(user);
            logger.warn("new user is ok");
            return "redirect:/users/1";
        }
        return "userlist";
    }

    @GetMapping("/ip")
    public String testIp(ModelMap map) {
        String hostAddress = getMyIp();
        logger.info("myip: " + hostAddress);
        map.put("myip", hostAddress);
        return "testip";
    }

    @RequestMapping("/iptest")
    public String test(ModelMap map) {
        map.put("myip", "th:text 设置文本内容 <b>加粗</b>");
        return "testip";
    }


    @SuppressWarnings("rawtypes")
    public static String getMyIp() {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
        try {
            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            boolean finded = false;// 是否找到外网IP
            while (netInterfaces.hasMoreElements() && !finded) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                Enumeration address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = (InetAddress) address.nextElement();
                    if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                        netip = ip.getHostAddress();
                        finded = true;
                        break;
                    } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                        localip = ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }
}
