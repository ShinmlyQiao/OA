package com.xingxing.oa.domain.entity;

import com.xingxing.oa.domain.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private Long id;

    private String name;

    private Gender gender;

    private LocalDate birthday;

    private String phone;

    private String email;

    private String qq;

    private Long address;

    private String addressDetail;

    private String idCard;

}
