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
public class BillTemp implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;

    /**
     * 对应的账本ID
     */
    private String accountBookId;

    /**
     * 发起者ID
     */
    private String accountBookUser;

    /**
     * 账单类型（收入/支出）
     */
    private String billType;

    /**
     * 金额
     */
    private Double money;

    /**
     * 消费类型
     */
    private String consumeType;

    /**
     * 钱包类型
     */
    private String walletType;

    /**
     * 备注
     */
    private String message;

    /**
     * 时间
     */
    private String date;

    /**
     * 图片
     */
    private String img;


}
