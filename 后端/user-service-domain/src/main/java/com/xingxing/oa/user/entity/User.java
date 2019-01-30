package com.xingxing.oa.user.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingxing.oa.annotions.GeneratorId;
import com.xingxing.oa.user.common.IdValueConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "User",description = "用户")
public class User {

    @ApiModelProperty(value = "id")
    @GeneratorId(value = IdValueConstants.USER_ID)
    private Long id;

    @ApiModelProperty(value="租户id")
    private Long tenantId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "英文名")
    private String englishName;

    @ApiModelProperty(value = "中文拼音大写名")
    private String spellName;

    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "头像唯一码")
    private String headImageId;

    @ApiModelProperty(value = "性别id")
    private Long genderId;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "出生日期")
    private LocalDate birthday;

    @ApiModelProperty(value = "民族id")
    private Long nationId;

    @ApiModelProperty(value = "国家id")
    private Long countryId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "qq号")
    private String qq;

    @ApiModelProperty(value = "微信号")
    private String wechat;

    @ApiModelProperty(value = "地址id")
    private Long addressId;

    @ApiModelProperty(value = "详细地址")
    private String addressDetail;

    @JsonIgnore
    @JSONField(serialize = false)
    @ApiModelProperty(value = "身份证",hidden = true)
    private String idCard;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JSONField(deserialize = false)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JSONField(deserialize = false)
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JSONField(deserialize = false)
    @ApiModelProperty(value = "创建者id")
    private Long createUserId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JSONField(deserialize = false)
    @ApiModelProperty(value = "最后一次更新者id")
    private Long updateUserId;

    @ApiModelProperty("是否删除")
    private Boolean deleted;

    public User() {
    }

    public User(Long id, Long tenantId, String name, String englishName, String spellName, String loginName,
                String headImageId, Long genderId, Integer age, LocalDate birthday, Long nationId,
                Long countryId, String phone, String email, String qq, String wechat, Long addressId,
                String addressDetail, String idCard, LocalDateTime createTime, LocalDateTime updateTime,
                Long createUserId, Long updateUserId, Boolean deleted) {
        this.id = id;
        this.tenantId = tenantId;
        this.name = name;
        this.englishName = englishName;
        this.spellName = spellName;
        this.loginName = loginName;
        this.headImageId = headImageId;
        this.genderId = genderId;
        this.age = age;
        this.birthday = birthday;
        this.nationId = nationId;
        this.countryId = countryId;
        this.phone = phone;
        this.email = email;
        this.qq = qq;
        this.wechat = wechat;
        this.addressId = addressId;
        this.addressDetail = addressDetail;
        this.idCard = idCard;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.createUserId = createUserId;
        this.updateUserId = updateUserId;
        this.deleted = deleted;
    }


    /**
     * 初始化字段
     */
    public void init(){
        this.updateTime = this.createTime = LocalDateTime.now();
        this.deleted = Boolean.FALSE;
    }
}
