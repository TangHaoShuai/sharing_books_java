package com.haoshuai.accountbook.entity.model;

import com.haoshuai.accountbook.entity.AccountBook;
import com.haoshuai.accountbook.entity.Bill;
import com.haoshuai.accountbook.entity.BillTemp;
import com.haoshuai.accountbook.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InformModel {
    private String uuid;

    /**
     * 发送者
     */
    private User userA;

    /**
     * 接收者
     */
    private User userB;

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

    private Bill billId;

    /**
     * 临时账目
     */
    private BillTemp billTemp;

    /**
     * 对应的账本ID
     */
    private AccountBook accountBook;
}
