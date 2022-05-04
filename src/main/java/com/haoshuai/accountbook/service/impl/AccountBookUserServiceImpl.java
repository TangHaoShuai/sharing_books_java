package com.haoshuai.accountbook.service.impl;

import com.haoshuai.accountbook.entity.AccountBookUser;
import com.haoshuai.accountbook.entity.User;
import com.haoshuai.accountbook.mapper.AccountBookUserMapper;
import com.haoshuai.accountbook.service.IAccountBookUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-04-19
 */
@Service
public class AccountBookUserServiceImpl extends ServiceImpl<AccountBookUserMapper, AccountBookUser> implements IAccountBookUserService {
@Autowired
private AccountBookUserMapper accountBookUserMapper;

    @Override
    public List<User> getNoAccountBookUser(String userid, String account_book_id) {
        return accountBookUserMapper.getNoAccountBookUser(userid,account_book_id);
    }
}
