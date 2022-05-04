package com.haoshuai.accountbook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.haoshuai.accountbook.entity.AccountBook;
import com.haoshuai.accountbook.entity.AccountBookUser;
import com.haoshuai.accountbook.entity.Bill;
import com.haoshuai.accountbook.entity.User;
import com.haoshuai.accountbook.entity.model.AccountBookModel;
import com.haoshuai.accountbook.service.IAccountBookService;
import com.haoshuai.accountbook.service.IAccountBookUserService;
import com.haoshuai.accountbook.service.IBillService;
import com.haoshuai.accountbook.service.IUserService;
import com.haoshuai.accountbook.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * 账簿控制器
 * 前端控制器
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/account-book")
public class AccountBookController {
    @Autowired
    private IAccountBookService iAccountBookService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IAccountBookUserService iAccountBookUserService;

    @Autowired
    private IBillService iBillService;


    @PostMapping("deleteAccountBook")
    public boolean deleteAccountBook (@RequestBody AccountBook accountBook) {
        if (Util.isStringNull(accountBook.getUuid())){
            QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
            billQueryWrapper.eq("account_book_id",accountBook.getUuid());
            List<Bill>billList =iBillService.list(billQueryWrapper);
            for (Bill b :billList){
                if (Util.isStringNull(b.getImg())){
                    String path = Util.BILL_PATH+b.getImg();
                    File file = new File(path);
                    if (file.exists()){
                        file.delete();
                    }
                }
            }
            QueryWrapper<AccountBook>accountBookQueryWrapper = new QueryWrapper<>();
            accountBookQueryWrapper.eq("uuid",accountBook.getUuid());
            return iAccountBookService.remove(accountBookQueryWrapper);
        }else {
            return false;
        }
    }


    /**
     * 更新账本名字
     * @param accountBook
     * @return
     */
    @PostMapping("updateAccountBookName")
    public boolean updateAccountBookName(@RequestBody AccountBook accountBook) {
        if (Util.isStringNull(accountBook.getUuid()) && Util.isStringNull(accountBook.getName())) {
            UpdateWrapper<AccountBook> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("uuid", accountBook.getUuid());
            updateWrapper.set("name", accountBook.getName());
            return iAccountBookService.update(null, updateWrapper);
        } else {
            return false;
        }
    }

    /**
     * 用户不在成员组的账本
     * @param user
     * @return
     */
    @PostMapping("getAccountBooks")
    public List<AccountBook> getAccountBooks(@RequestBody User user) {
        if (Util.isStringNull(user.getUuid())) {
//            List<AccountBook>accountBooks = iAccountBookService.list();
//            List<AccountBook>accountBookList = iAccountBookService.getUserAccountBooks(user);
//            List<AccountBook> list = new ArrayList<>();
//            for (int i = 0; i < accountBooks.size(); i++) {
//                boolean tag = true;
//                for (int j = 0; j < accountBookList.size(); j++) {
//                    if (accountBooks.get(i).getUuid().equals(accountBookList.get(j).getUuid())){
//                        tag = false;
//                        break;
//                    }
//                }
//                if (tag){
//                    list.add(accountBooks.get(i));
//                }
//            }
            List<AccountBook> list = iAccountBookService.getUserNoAccountBooks(user);
            return list;
        } else {
            return null;
        }
    }

    /**
     * 根据ID 查询账本
     * @param accountBook
     * @return
     */
    @PostMapping("getAccountBook")
    public AccountBook getAccountBook(@RequestBody AccountBook accountBook) {
        if (Util.isStringNull(accountBook.getUuid())) {
            QueryWrapper<AccountBook> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid", accountBook.getUuid());
            return iAccountBookService.getOne(queryWrapper);
        }
        return null;
    }

    /**
     * 获取所有账本数据
     *
     * @param user
     * @return
     */
    @PostMapping("getAccountBookList")
    public List<AccountBookModel> getAccountBookList(@RequestBody User user) {
        List<AccountBookModel> accountBookModels = new ArrayList<>();
        List<AccountBook> accountBooks = iAccountBookService.getUserAccountBooks(user);
        for (AccountBook a : accountBooks) {
            AccountBookModel accountBookModel = new AccountBookModel();
            BeanUtils.copyProperties(a, accountBookModel); //拷贝字段相同

            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("uuid", a.getAccountBookAdmin());
            User tempUser = iUserService.getOne(userQueryWrapper);//查询管理员信息
            accountBookModel.setAccountBookAdmin(tempUser);

            QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
            billQueryWrapper.eq("account_book_id", a.getUuid());
            List<Bill> billList = iBillService.list(billQueryWrapper); //查询账本对应的账单
            double income = 0;//账本总收入
            double expend = 0;//账本总支出
            double surplus = 0;//账本结余
            for (Bill b : billList) {
                if (b.getBillType().equals("收入")) {
                    income += b.getMoney();
                } else if (b.getBillType().equals("支出")) {
                    expend += b.getMoney();
                }
            }
            surplus = income - expend;
            accountBookModel.setSurplus(surplus);
            accountBookModel.setIncome(income);
            accountBookModel.setExpend(expend);

            QueryWrapper<AccountBookUser> accountBookUserQueryWrapper = new QueryWrapper<>();
            accountBookUserQueryWrapper.eq("account_book_id", a.getUuid());
            List<AccountBookUser> accountBookUsers = iAccountBookUserService.list(accountBookUserQueryWrapper);//查询成员
            List<User> users = new ArrayList<>();
            for (AccountBookUser s : accountBookUsers) {
                QueryWrapper<User> userQueryWrapper1 = new QueryWrapper<>();
                userQueryWrapper1.eq("uuid", s.getUserId());
                User temp_user = iUserService.getOne(userQueryWrapper1); //获取成员详细信息
                users.add(temp_user);
            }
            accountBookModel.setUsers(users); //获取账本管理员信息
            accountBookModel.setCount(users.size() + 1);//获取成员数量
            accountBookModels.add(accountBookModel);
        }
        //排序
        accountBookModels.sort(Comparator.comparing(AccountBookModel::getName));
        return accountBookModels;
    }


    /**
     * 添加账本
     *
     * @param accountBook
     * @return
     */
    @PostMapping("addAccountBook")
    public boolean addAccountBook(@RequestBody AccountBook accountBook) {
        if (Util.isStringNull(accountBook.getName()) &&
                Util.isStringNull(accountBook.getAccountBookType()) &&
                Util.isStringNull(accountBook.getAccountBookAdmin())) {
            accountBook.setUuid(Util.getUUID());
            return iAccountBookService.save(accountBook);
        } else {
            return false;
        }
    }
}
