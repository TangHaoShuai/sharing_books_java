package com.haoshuai.accountbook.entity.model;

import com.haoshuai.accountbook.entity.User;

public class BillModel {

    private String uuid;
    /**
     * 对应的账本ID
     */
    private String accoutBookId;

    /**
     * 发起者ID
     */
    private User accoutBookUser;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAccoutBookId() {
        return accoutBookId;
    }

    public void setAccoutBookId(String accoutBookId) {
        this.accoutBookId = accoutBookId;
    }

    public User getAccoutBookUser() {
        return accoutBookUser;
    }

    public void setAccoutBookUser(User accoutBookUser) {
        this.accoutBookUser = accoutBookUser;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(String consumeType) {
        this.consumeType = consumeType;
    }

    public String getWalletType() {
        return walletType;
    }

    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
