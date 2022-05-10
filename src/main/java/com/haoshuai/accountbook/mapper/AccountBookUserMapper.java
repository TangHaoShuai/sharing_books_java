package com.haoshuai.accountbook.mapper;

import com.haoshuai.accountbook.entity.AccountBookUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haoshuai.accountbook.entity.User;
import com.haoshuai.accountbook.entity.model.AccountBookUserModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-04-19
 */
public interface AccountBookUserMapper extends BaseMapper<AccountBookUser> {

    List<User> getNoAccountBookUser(@Param("userid") String userid, @Param("account_book_id") String account_book_id);

    List<AccountBookUserModel> getAccountBookUserModel(@Param("account_book_id") String account_book_id);

}
