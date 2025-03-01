package com.jasonchow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jasonchow.constant.MessageConstant;
import com.jasonchow.dto.UserLoginDTO;
import com.jasonchow.entity.User;
import com.jasonchow.exception.AccountNotFoundException;
import com.jasonchow.exception.PasswordErrorException;
import com.jasonchow.exception.RegistryFailedException;
import com.jasonchow.mapper.UserMapper;
import com.jasonchow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        //// 根据用户名返回用户对象
        User user = userMapper.getByUsername(userLoginDTO.getUsername());

        //// 如果用户为空，返回异常（账号不存在）
        if(user == null) {
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        //// 如果用户不为空，但密码不匹配（记得加密），那么返回密码错误异常
        String password = userLoginDTO.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!user.getPassword().equals(password)) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        //// 登陆成功，返回对象
        return user;
    }

    /**
     * 员工注册
     * @param user
     */
    @Override
    public void registryUser(User user) {
        //// 先根据账号名检查是否已经存在该用户
        User user1 = userMapper.getByUsername(user.getUsername());
        if(user1 != null) {
            /// 账号已经存在
            throw new RegistryFailedException(MessageConstant.ACCOUNT_USED);
        }
        //// 将密码加密
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(password);
        // TODO:切面类暂时不能用，先自己插入数据先
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        //// 可以插入数据
        userMapper.insertUser(user);
    }
}
