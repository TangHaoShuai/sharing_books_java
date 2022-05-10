package com.haoshuai.accountbook.service.impl;

import com.haoshuai.accountbook.entity.AccountBookLog;
import com.haoshuai.accountbook.mapper.AccountBookLogMapper;
import com.haoshuai.accountbook.service.IAccountBookLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-05-10
 */
@Service
public class AccountBookLogServiceImpl extends ServiceImpl<AccountBookLogMapper, AccountBookLog> implements IAccountBookLogService {

    @Autowired
    private AccountBookLogMapper accountBookLogMapper;


}
