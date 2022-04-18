package com.atguigu.crud.service;

import com.atguigu.crud.bean.User;
import com.atguigu.crud.bean.UserExample;
import com.atguigu.crud.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getUserList() {
        return userMapper.selectByExample(null);
    }

    public User getUserByLogin(String userName, String password) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andUsernameEqualTo(userName)
                .andUserpasswordEqualTo(password);

        List<User> userList = userMapper.selectByExample(userExample);
        if(userList != null && userList.size() >0){
            return userList.get(0);
        }

        return null;
    }
}
