package com.jasonchow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jasonchow.Enum.dbOperationType;
import com.jasonchow.annotation.AutoFill;
import com.jasonchow.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名返回用户对象
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username}")
    User getByUsername(String username);

    /**
     * 插入用户数据
     * @param userRegistryDTO
     */
    //@AutoFill(dbOperationType.INSERT)
    void insertUser(User userRegistryDTO);
}
