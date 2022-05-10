package com.haoshuai.accountbook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.haoshuai.accountbook.entity.AccountBookUser;
import com.haoshuai.accountbook.entity.User;
import com.haoshuai.accountbook.entity.model.AccountBookUserModel;
import com.haoshuai.accountbook.entity.model.UserVo;
import com.haoshuai.accountbook.service.IAccountBookUserService;
import com.haoshuai.accountbook.service.IUserService;
import com.haoshuai.accountbook.utils.Util;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @PostMapping("getJurisdiction")
    public boolean getJurisdiction(@RequestBody AccountBookUser accountBookUser) {
        if (Util.isStringNull(accountBookUser.getAccountBookId()) && Util.isStringNull(accountBookUser.getUserId())) {
            QueryWrapper<AccountBookUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account_book_id", accountBookUser.getAccountBookId());
            queryWrapper.eq("user_id", accountBookUser.getUserId());
            queryWrapper.eq("administrator", 1);
            return iAccountBookUserService.getOne(queryWrapper) != null;

        } else {
            return false;
        }
    }

    /**
     * 设置管理员
     *
     * @param accountBookUser
     * @return
     */
    @PostMapping("setAdministrator")
    public boolean setAdministrator(@RequestBody AccountBookUser accountBookUser) {
        if (Util.isStringNull(accountBookUser.getUuid())) {
            UpdateWrapper<AccountBookUser> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("uuid", accountBookUser.getUuid());
            updateWrapper.set("administrator", 1);
            return iAccountBookUserService.update(null, updateWrapper);
        } else {
            return false;
        }
    }

    /**
     * 取消管理员
     *
     * @param accountBookUser
     * @return
     */
    @PostMapping("deleteAdministrator")
    public boolean deleteAdministrator(@RequestBody AccountBookUser accountBookUser) {
        if (Util.isStringNull(accountBookUser.getUuid())) {
            UpdateWrapper<AccountBookUser> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("uuid", accountBookUser.getUuid());
            updateWrapper.set("administrator", 0);
            return iAccountBookUserService.update(null, updateWrapper);
        } else {
            return false;
        }
    }

    /**
     * 删除账本成员
     *
     * @param accountBookUser
     * @return
     */
    @PostMapping("deleteAccountBookUser")
    public boolean deleteAccountBookUser(@RequestBody AccountBookUser accountBookUser) {
        if (Util.isStringNull(accountBookUser.getUuid())) {
            QueryWrapper<AccountBookUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid", accountBookUser.getUuid());
            return iAccountBookUserService.remove(queryWrapper);
        } else {
            return false;
        }
    }

    /**
     * 成员管理 成员列表
     *
     * @param accountBookUser
     * @return
     */
    @PostMapping("getAccountBookUser")
    public List<AccountBookUserModel> getAccountBookUser(@RequestBody AccountBookUser accountBookUser) {
        if (Util.isStringNull(accountBookUser.getAccountBookId())) {
            return iAccountBookUserService.getAccountBookUserModel(accountBookUser.getAccountBookId());
        } else {
            return null;
        }
    }

    @PostMapping("getUsers")
    public List<UserVo> getUsers(@RequestBody Map<String, String> map) {
        String phone = map.get("phone");
        String account_book_id = map.get("account_book_id");
        List<UserVo> userVoList = new ArrayList<>();
        if (Util.isStringNull(phone) && Util.isStringNull(account_book_id)) {
            UserVo userVo = new UserVo();
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone", phone);
            User user = iUserService.getOne(queryWrapper);
            BeanUtils.copyProperties(user, userVo); //拷贝字段相同

            QueryWrapper<AccountBookUser> accountBookUserQueryWrapper = new QueryWrapper<>();
            accountBookUserQueryWrapper.eq("account_book_id", account_book_id);
            accountBookUserQueryWrapper.eq("user_id", user.getUuid());
            AccountBookUser accountBookUser = iAccountBookUserService.getOne(accountBookUserQueryWrapper);
            if (accountBookUser != null) {
                userVo.setExist(true);
            } else {
                userVo.setExist(false);
            }
            userVoList.add(userVo);
            return userVoList;
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
