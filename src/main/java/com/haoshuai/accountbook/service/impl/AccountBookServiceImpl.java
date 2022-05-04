package com.haoshuai.accountbook.service.impl;

import com.haoshuai.accountbook.entity.AccountBook;
import com.haoshuai.accountbook.entity.User;
import com.haoshuai.accountbook.mapper.AccountBookMapper;
import com.haoshuai.accountbook.service.IAccountBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-04-19
 */
@Service
public class AccountBookServiceImpl extends ServiceImpl<AccountBookMapper, AccountBook> implements IAccountBookService {
    @Autowired
    private AccountBookMapper accountBookMapper;


    @Override
    public List<AccountBook> getUserAccountBooks(User user) {
        return accountBookMapper.getUserAccountBooks(user);
    }

    @Override
    public List<AccountBook> getUserNoAccountBooks(User user) {
        return accountBookMapper.getUserNoAccountBooks(user);
    }
}
