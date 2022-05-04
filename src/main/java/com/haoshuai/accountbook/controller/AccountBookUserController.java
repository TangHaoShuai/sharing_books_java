package com.haoshuai.accountbook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haoshuai.accountbook.entity.AccountBookUser;
import com.haoshuai.accountbook.entity.User;
import com.haoshuai.accountbook.service.IAccountBookUserService;
import com.haoshuai.accountbook.service.IUserService;
import com.haoshuai.accountbook.utils.Util;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 账本成员
 * 前端控制器
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/account-book-user")
public class AccountBookUserController {
    @Autowired
    private IAccountBookUserService iAccountBookUserService;
    @Autowired
    private IUserService iUserService;

    @PostMapping("deleteAccountBookUser")
    public boolean deleteAccountBookUser(@RequestBody AccountBookUser accountBookUser) {
        if (Util.isStringNull(accountBookUser.getAccountBookId()) && Util.isStringNull(accountBookUser.getUserId())) {
            QueryWrapper<AccountBookUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account_book_id", accountBookUser.getAccountBookId());
            queryWrapper.eq("user_id", accountBookUser.getUserId());
            return iAccountBookUserService.remove(queryWrapper);
        } else {
            return false;
        }
    }

    @PostMapping("getAccountBookUser")
    public List<User> getAccountBookUser(@RequestBody AccountBookUser accountBookUser) {
        if (Util.isStringNull(accountBookUser.getAccountBookId())) {
            List<User> users = new ArrayList<>();
            QueryWrapper<AccountBookUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account_book_id", accountBookUser.getAccountBookId());
            List<AccountBookUser> accountBookUsers = iAccountBookUserService.list(queryWrapper);
            for (AccountBookUser a : accountBookUsers) {
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("uuid", a.getUserId());
                User user = iUserService.getOne(userQueryWrapper);
                if (user != null) {
                    users.add(user);
                }
            }
            return users;
        } else {
            return null;
        }
    }

    @GetMapping("getNoAccountBookUser")
    public List<User> getNoAccountBookUser(@Param("userid") String userid, @Param("account_book_id") String account_book_id) {
        if (Util.isStringNull(userid) && Util.isStringNull(account_book_id)) {
            return iAccountBookUserService.getNoAccountBookUser(userid, account_book_id);
        } else {
            return null;
        }
    }

    @PostMapping("addAccountBookUser")
    public boolean addAccountBookUser(@RequestBody AccountBookUser accountBookUser) {
        if (Util.isStringNull(accountBookUser.getUserId()) && Util.isStringNull(accountBookUser.getAccountBookId())) {
            accountBookUser.setUuid(Util.getUUID());
            return iAccountBookUserService.save(accountBookUser);
        } else {
            return false;
        }
    }


}
