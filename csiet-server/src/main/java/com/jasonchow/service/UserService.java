package com.jasonchow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jasonchow.dto.UserLoginDTO;
import com.jasonchow.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends IService<User> {

    /**
     * 登录实现
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO);

    /**
     * 用户注册
     * @param user
     */
    void registryUser(User user);
}
