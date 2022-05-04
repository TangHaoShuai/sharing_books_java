package com.haoshuai.accountbook.entity.model;

import com.haoshuai.accountbook.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AccountBookModel {
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
    private User accountBookAdmin;

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
     * 成员数
     */
    private int count;

    /**
     * 成员集合
     */
    private List<User> users;


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

    public User getAccountBookAdmin() {
        return accountBookAdmin;
    }

    public void setAccountBookAdmin(User accountBookAdmin) {
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
