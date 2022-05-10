package com.haoshuai.accountbook.entity.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AccountBookVo implements Serializable {



    private String uuid;

    /**
     * 账本名字
     */
    private String name;

    /**
     * 账本类型
     */
    private String accountBookType;

    /**
     * 账本管理员
     */
    private String accountBookAdmin;

    /**
     * 账本总收入
     */
    private Double income;

    /**
     * 账本总支出
     */
    private Double expend;

    /**
     * 账本结余
     */
    private Double surplus;


    /**
     * 是否已经存在
     */
    private boolean isExist;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountBookType() {
        return accountBookType;
    }

    public void setAccountBookType(String accountBookType) {
        this.accountBookType = accountBookType;
    }

    public String getAccountBookAdmin() {
        return accountBookAdmin;
    }

    public void setAccountBookAdmin(String accountBookAdmin) {
        this.accountBookAdmin = accountBookAdmin;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getExpend() {
        return expend;
    }

    public void setExpend(Double expend) {
        this.expend = expend;
    }

    public Double getSurplus() {
        return surplus;
    }

    public void setSurplus(Double surplus) {
        this.surplus = surplus;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }
}
