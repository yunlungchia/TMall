package com.personal.tmall.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;

    private String nickname;

    private String password;

    private String salt;

    private String head;

    private Date registerData;

    private Date lastLoginData;

    private Integer loginCount;
}