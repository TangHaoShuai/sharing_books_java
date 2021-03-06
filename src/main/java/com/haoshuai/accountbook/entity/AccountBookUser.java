package com.haoshuai.accountbook.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AccountBookUser implements Serializable {

    private static final long serialVersionUID = 1L;

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
}
