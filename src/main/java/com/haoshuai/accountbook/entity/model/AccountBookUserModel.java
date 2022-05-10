package com.haoshuai.accountbook.entity.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AccountBookUserModel {
    private String uuid;

    /**
     * 账本ID
     */
    private String accountBookId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 管理员标识符
     */
    private int administrator;

    /**
     * 用户ID
     */
    private String userUuid;

    /**
     * 用户名字
     */
    private String userName;

    /**
     * 用户手机号
     */
    private String userPhone;

    private String userUrl;



















}
