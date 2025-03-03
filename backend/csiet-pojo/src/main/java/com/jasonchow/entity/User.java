package com.jasonchow.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "用户信息类")
public class User {

    @TableId
    @Schema(name = "主键")
    private Integer id;

    @Schema(name = "姓名")
    private String name;

    @Schema(name = "账号名")
    private String username;

    @Schema(name = "密码")
    private String password;

    @Schema(name = "手机号")
    private String phone;

    @Schema(name = "性别")
    private String sex;

    @Schema(name = "身份证号")
    private String idNumber;

    @Schema(name = "邮箱")
    private String email;

    @Schema(name = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "更改时间")
    private LocalDateTime updateTime;
}
