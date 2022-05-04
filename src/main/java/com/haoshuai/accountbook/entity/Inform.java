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
public class Inform implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String AUDIT = "账本审核";
    public static final String INVITE = "邀请加入账本";
    public static final String APPLY_FOR = "申请加入账本";

    private String uuid;

    /**
     * 发送者ID
     */
    private String userA;

    /**
     * 接收者ID
     */
    private String userB;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 时间
     */
    private String date;

    /**
     * 消息类型
     */
    private String type;

    /**
     * 账目ID
     */

    private String billId;

    /**
     * 临时账目ID
     */
    private String billTempId;

    /**
     * 对应的账本ID
     */
    private String accountBookId;
}
