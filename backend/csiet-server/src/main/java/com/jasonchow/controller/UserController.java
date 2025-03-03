package com.jasonchow.controller;

import com.jasonchow.constant.JwtClaimsConstant;
import com.jasonchow.dto.UserLoginDTO;
import com.jasonchow.entity.User;
import com.jasonchow.properties.JwtProperties;
import com.jasonchow.result.Result;
import com.jasonchow.service.UserService;
import com.jasonchow.utils.JwtUtil;
import com.jasonchow.vo.UserLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "用户接口实现")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    @Operation(summary = "登录接口")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);
        User user = userService.login(userLoginDTO);
        //// 为用户生成token
        Map map = new HashMap();
        map.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), map);
        //// 封装到VO
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .token(token)
                .build();
        //// 返回结果
        return Result.success(userLoginVO);
    }

    @PostMapping("/registry")
    @Operation(summary = "注册接口")
    public Result registryUser(@RequestBody User user) {
        log.info("用户注册：{}",user);
        userService.registryUser(user);
        return Result.success();
    }
}
