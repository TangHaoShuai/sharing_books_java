package com.haoshuai.accountbook.service;

import com.haoshuai.accountbook.entity.AccountBook;
import com.baomidou.mybatisplus.extension.service.IService;
import com.haoshuai.accountbook.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-04-19
 */
public interface IAccountBookService extends IService<AccountBook> {
    List<AccountBook> getUserAccountBooks(User user);

    List<AccountBook> getUserNoAccountBooks(User user);
}
