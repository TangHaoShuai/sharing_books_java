package com.haoshuai.accountbook.service;

import com.haoshuai.accountbook.entity.AccountBookUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.haoshuai.accountbook.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-04-19
 */
public interface IAccountBookUserService extends IService<AccountBookUser> {
    List<User> getNoAccountBookUser(@Param("userid") String userid, @Param("account_book_id") String account_book_id);
}
