package com.haoshuai.accountbook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haoshuai.accountbook.entity.AccountBookLog;
import com.haoshuai.accountbook.service.IAccountBookLogService;
import com.haoshuai.accountbook.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-05-10
 */
@RestController
@RequestMapping("/account-book-log")
public class AccountBookLogController {
    @Autowired
    private IAccountBookLogService iAccountBookLogService;

    /**
     * 添加日志
     *
     * @param accountBookLog
     */
    @PostMapping("addAccountBookLog")
    public void addAccountBookLog(@RequestBody AccountBookLog accountBookLog) {
        if (Util.isStringNull(accountBookLog.getAccountBookId()) && Util.isStringNull(accountBookLog.getMessage())) {
            accountBookLog.setUuid(Util.getUUID());
            accountBookLog.setDate(Util.getDate());
            iAccountBookLogService.save(accountBookLog);
        }
    }

    @PostMapping("getAccountBookLog")
    public List<AccountBookLog> getAccountBookLog(@RequestBody AccountBookLog accountBookLog) {
        if (Util.isStringNull(accountBookLog.getAccountBookId())) {
            QueryWrapper<AccountBookLog> accountBookLogQueryWrapper = new QueryWrapper<>();
            accountBookLogQueryWrapper.eq("account_book_id", accountBookLog.getAccountBookId());
            List<AccountBookLog> accountBookLogs = iAccountBookLogService.list(accountBookLogQueryWrapper);
            accountBookLogs.sort(Comparator.comparing(AccountBookLog::getDate));
            Collections.reverse(accountBookLogs);
            return accountBookLogs;
        } else {
            return null;
        }
    }

}
