package com.atguigu.crud.controller;

import com.atguigu.crud.bean.Department;
import com.atguigu.crud.bean.Msg;
import com.atguigu.crud.bean.User;
import com.atguigu.crud.service.DepartmentService;
import com.atguigu.crud.service.UserService;
import com.atguigu.crud.utils.FtpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    RedisConnectionFactory redisConnectionFactory;

    /**
     * 添加用户，含文件上传
     * @param user
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("addUser")
    public Object addUser(User user, @RequestParam("headPic") MultipartFile file) throws IOException {
        //调用自定义的FTP工具类上传文件
        String fileName = FtpUtil.uploadFile(file);

        if(!StringUtils.isEmpty(fileName)){
            //设置对象中的图片名
            //user.setPicPath(fileName);

            //添加用户到数据库
            //userService.addUser(user);
        }

//        return new Result(fileName,"添加用户成功",100);
        return Msg.success().add("fileName",fileName);
    }

    @RequestMapping("getUserList")
    public Object getUserList(){
        List<User> userList = userService.getUserList();

//        return new Result(userList,"获取用户列表成功",100);
        return Msg.success().add("userList",userList);
    }

//    /**
//     * 登录
//     * @param userName
//     * @param password
//     * @param response
//     * @return
//     * @throws UnsupportedEncodingException
//     */
//    @PostMapping("/login")
//    public Msg login(@RequestParam String userName, @RequestParam String password, HttpServletResponse response) throws UnsupportedEncodingException {
//        User user = userService.getUserByLogin(userName,password);
//        if(user != null){//登录成功
//            //生成Token令牌
//            String token = UUID.randomUUID()+"";
//            //存到Redis数据库
//            redisTemplate.opsForValue().set(token,user, Duration.ofMinutes(30L));
//
////            return new Result(token,"登录成功",100);
//            return Msg.success();
//        }
//
////        return new Result(null,"登录失败",104);
//        return Msg.fail();
//    }

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/login")
    @ResponseBody
    public Msg getLogin(){
        //查出的所有部门信息
        List<Department> list = departmentService.getDepts();

        //生成Token令牌
        String token = UUID.randomUUID()+"";
        //存到Redis数据库
        redisTemplate.opsForValue().set(token,list.toString(), Duration.ofMinutes(30L));

        return Msg.success().add("loginInfo", token);
    }
}
