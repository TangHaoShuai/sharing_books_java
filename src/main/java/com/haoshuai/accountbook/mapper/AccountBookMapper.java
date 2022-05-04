package com.haoshuai.accountbook.mapper;

import com.haoshuai.accountbook.entity.AccountBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haoshuai.accountbook.entity.User;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-04-19
 */
public interface AccountBookMapper extends BaseMapper<AccountBook> {
    List<AccountBook> getUserAccountBooks(User user);

    List<AccountBook> getUserNoAccountBooks(User user);
}
