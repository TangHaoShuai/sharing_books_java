package com.haoshuai.accountbook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haoshuai.accountbook.entity.*;
import com.haoshuai.accountbook.entity.model.InformModel;
import com.haoshuai.accountbook.service.*;
import com.haoshuai.accountbook.utils.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 通知
 * 前端控制器
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/inform")
public class InformController {


    @Autowired
    private IInformService iInformService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IAccountBookService iAccountBookService;

    @Autowired
    private IBillTempService iBillTempService;

    @Autowired
    private IBillService iBillService;

    @Autowired
    private IAccountBookUserService iAccountBookUserService;

    @PostMapping("getInformByUuid")
    public Inform getInformByUuid(@RequestBody Inform inform) {
        if (Util.isStringNull(inform.getUuid())) {
            QueryWrapper<Inform> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid", inform.getUuid());
            return iInformService.getOne(queryWrapper);
        } else {
            return null;
        }
    }

    @PostMapping("consent")
    public boolean consent(@RequestBody InformModel informModel) {
        if (informModel != null) {
            if (informModel.getType().equals(Inform.AUDIT)) {
                QueryWrapper<BillTemp> billTempQueryWrapper = new QueryWrapper<>();
                billTempQueryWrapper.eq("uuid", informModel.getBillTemp().getUuid());
                BillTemp billTemp = iBillTempService.getOne(billTempQueryWrapper);
                Bill bill = new Bill();
                BeanUtils.copyProperties(billTemp, bill); //拷贝字段相同
                iBillService.save(bill);
                QueryWrapper<Inform> informQueryWrapper = new QueryWrapper<>();
                informQueryWrapper.eq("uuid", informModel.getUuid());
                return iInformService.remove(informQueryWrapper);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    @PostMapping("addInform")
    public boolean addInform(@RequestBody Inform inform) {
        if (Util.isStringNull(inform.getUserA()) && Util.isStringNull(inform.getUserB()) && Util.isStringNull(inform.getType())) {
            if (inform.getType().equals(Inform.AUDIT)) {
                //账本审核
                inform.setUuid(Util.getUUID());
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("uuid", inform.getUserA());
                User user = iUserService.getOne(userQueryWrapper);//获取发送者信息
                QueryWrapper<AccountBook> accountBookQueryWrapper = new QueryWrapper<>();
                accountBookQueryWrapper.eq("uuid", inform.getAccountBookId());
                AccountBook accountBook = iAccountBookService.getOne(accountBookQueryWrapper);//获取账本信息
                //拼接消息
                String message = "账本:[" + accountBook.getName() + "]有一条来着成员:[" + user.getUsername() + ":" + user.getPhone() + "]的待审核账目。";
                inform.setMessage(message);
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
                inform.setDate(df.format(new Date()));
                return iInformService.save(inform);
            } else if (inform.getType().equals(Inform.INVITE)) {
                //邀请加入账本
                inform.setUuid(Util.getUUID());
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("uuid", inform.getUserA());
                User user = iUserService.getOne(userQueryWrapper);//获取发送者信息
                QueryWrapper<AccountBook> accountBookQueryWrapper = new QueryWrapper<>();
                accountBookQueryWrapper.eq("uuid", inform.getAccountBookId());
                AccountBook accountBook = iAccountBookService.getOne(accountBookQueryWrapper);//获取账本信息
                //拼接消息
                String message = "用户[" + user.getUsername() + "-" + user.getPhone() + "]邀请您加入账本[" + accountBook.getName() + "]一起编辑账本。";
                inform.setMessage(message);
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
                inform.setDate(df.format(new Date()));
                return iInformService.save(inform);
            } else if (inform.getType().equals(Inform.APPLY_FOR)) {
                //申请加入账本
                inform.setUuid(Util.getUUID());
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("uuid", inform.getUserA());
                User user = iUserService.getOne(userQueryWrapper);//获取发送者信息
                QueryWrapper<AccountBook> accountBookQueryWrapper = new QueryWrapper<>();
                accountBookQueryWrapper.eq("uuid", inform.getAccountBookId());
                AccountBook accountBook = iAccountBookService.getOne(accountBookQueryWrapper);//获取账本信息
                //拼接消息
                String message = "用户[" + user.getUsername() + "-" + user.getPhone() + "]申请加入您的账本[" + accountBook.getName() + "]一起编辑账本。";
                inform.setMessage(message);
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
                inform.setDate(df.format(new Date()));
                return iInformService.save(inform);
            } else if (inform.getType().equals(Inform.BE_APPROVED)) {
                //审核通过
                inform.setUuid(Util.getUUID());
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("uuid", inform.getUserA());
                User user = iUserService.getOne(userQueryWrapper);//获取发送者信息
                QueryWrapper<AccountBook> accountBookQueryWrapper = new QueryWrapper<>();
                accountBookQueryWrapper.eq("uuid", inform.getAccountBookId());
                AccountBook accountBook = iAccountBookService.getOne(accountBookQueryWrapper);//获取账本信息
                //拼接消息
                String message = "您对账本:[" + accountBook.getName() + "]发起的一条账目审核已经通过！";
                inform.setMessage(message);
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
                inform.setDate(df.format(new Date()));
                return iInformService.save(inform);
            } else if (inform.getType().equals(Inform.AUDIT_FAILED)) {
//                审核不通过

                inform.setUuid(Util.getUUID());
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("uuid", inform.getUserA());
                User user = iUserService.getOne(userQueryWrapper);//获取发送者信息
                QueryWrapper<AccountBook> accountBookQueryWrapper = new QueryWrapper<>();
                accountBookQueryWrapper.eq("uuid", inform.getAccountBookId());
                AccountBook accountBook = iAccountBookService.getOne(accountBookQueryWrapper);//获取账本信息
                //拼接消息
                String message = "您对账本:[" + accountBook.getName() + "]发起的一条账目审核不通过！";
                inform.setMessage(message);
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
                inform.setDate(df.format(new Date()));
                return iInformService.save(inform);
            } else if (inform.getType().equals(Inform.APPLY_FOR_YES)) {
//                申请加入账本_通过

                inform.setUuid(Util.getUUID());
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("uuid", inform.getUserA());
                User user = iUserService.getOne(userQueryWrapper);//获取发送者信息
                QueryWrapper<AccountBook> accountBookQueryWrapper = new QueryWrapper<>();
                accountBookQueryWrapper.eq("uuid", inform.getAccountBookId());
                AccountBook accountBook = iAccountBookService.getOne(accountBookQueryWrapper);//获取账本信息
                //拼接消息
                String message = "您申请加入的账本:[" + accountBook.getName() + "]审核通过";
                inform.setMessage(message);
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
                inform.setDate(df.format(new Date()));
                return iInformService.save(inform);
            } else if (inform.getType().equals(Inform.APPLY_FOR_NO)) {
//          申请加入账本_不通过

                inform.setUuid(Util.getUUID());
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("uuid", inform.getUserA());
                User user = iUserService.getOne(userQueryWrapper);//获取发送者信息
                QueryWrapper<AccountBook> accountBookQueryWrapper = new QueryWrapper<>();
                accountBookQueryWrapper.eq("uuid", inform.getAccountBookId());
                AccountBook accountBook = iAccountBookService.getOne(accountBookQueryWrapper);//获取账本信息
                //拼接消息
                String message = "您申请加入的账本:[" + accountBook.getName() + "]审核不通过";
                inform.setMessage(message);
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
                inform.setDate(df.format(new Date()));
                return iInformService.save(inform);
            } else if (inform.getType().equals(Inform.INVITE_YES)) {
//                邀请加入账本_通过

                inform.setUuid(Util.getUUID());
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("uuid", inform.getUserA());
                User user = iUserService.getOne(userQueryWrapper);//获取发送者信息
                QueryWrapper<AccountBook> accountBookQueryWrapper = new QueryWrapper<>();
                accountBookQueryWrapper.eq("uuid", inform.getAccountBookId());
                AccountBook accountBook = iAccountBookService.getOne(accountBookQueryWrapper);//获取账本信息
                //拼接消息
                String message = "您邀请[" + user.getUsername() + "加入的账本:[" + accountBook.getName() + "]对方已经同意";
                inform.setMessage(message);
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
                inform.setDate(df.format(new Date()));
                return iInformService.save(inform);
            } else if (inform.getType().equals(Inform.INVITE_NO)) {
//                邀请加入账本_拒绝

                inform.setUuid(Util.getUUID());
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("uuid", inform.getUserA());
                User user = iUserService.getOne(userQueryWrapper);//获取发送者信息
                QueryWrapper<AccountBook> accountBookQueryWrapper = new QueryWrapper<>();
                accountBookQueryWrapper.eq("uuid", inform.getAccountBookId());
                AccountBook accountBook = iAccountBookService.getOne(accountBookQueryWrapper);//获取账本信息
                //拼接消息
                String message = "您邀请[" + user.getUsername() + "加入的账本:[" + accountBook.getName() + "]对方拒绝加入";
                inform.setMessage(message);
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
                inform.setDate(df.format(new Date()));
                return iInformService.save(inform);
            }
            return false;
        } else {
            return false;
        }
    }


    /**
     * 查询一条通知
     *
     * @param inform
     * @return
     */
    @PostMapping("getInform")
    public Inform getInform(@RequestBody Inform inform) {
        QueryWrapper<Inform> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_a", inform.getUserA());
        queryWrapper.eq("user_b", inform.getUserB());
        queryWrapper.eq("type", inform.getType());
        queryWrapper.eq("account_book_id", inform.getAccountBookId());
        return iInformService.getOne(queryWrapper);
    }

    /**
     * 查询根据接收人 查询通知
     *
     * @param inform
     * @return
     */
    @PostMapping("getUserBInforms")
    public List<InformModel> getUserBInforms(@RequestBody Inform inform) {
        List<InformModel> informModels = new ArrayList<>();

        QueryWrapper<Inform> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_b", inform.getUserB());
        List<Inform> informs = iInformService.list(queryWrapper);

        QueryWrapper<AccountBookUser> accountBookUserQueryWrapper = new QueryWrapper<>();
        accountBookUserQueryWrapper.eq("user_id", inform.getUserB());
        accountBookUserQueryWrapper.eq("administrator", 1);
        List<AccountBookUser> accountBookUsers = iAccountBookUserService.list(accountBookUserQueryWrapper);

        for (AccountBookUser a : accountBookUsers) {
            QueryWrapper<Inform> informQueryWrapper = new QueryWrapper<>();
            informQueryWrapper.eq("account_book_id", a.getAccountBookId());
            informQueryWrapper.and(wrapper -> wrapper.eq("type", Inform.AUDIT).or().eq("type", Inform.APPLY_FOR));
            List<Inform> informList = iInformService.list(informQueryWrapper);
            for (Inform i : informList) {
                informs.add(i);
            }
        }

        for (Inform s : informs) {
            QueryWrapper<User> userQueryWrapperA = new QueryWrapper<>();
            userQueryWrapperA.eq("uuid", s.getUserA());
            User userA = iUserService.getOne(userQueryWrapperA);
            QueryWrapper<User> userQueryWrapperB = new QueryWrapper<>();
            userQueryWrapperB.eq("uuid", s.getUserB());
            User userB = iUserService.getOne(userQueryWrapperB);
            QueryWrapper<BillTemp> billTempQueryWrapper = new QueryWrapper<>();
            billTempQueryWrapper.eq("uuid", s.getBillTempId());
            BillTemp billTemp = iBillTempService.getOne(billTempQueryWrapper);
            QueryWrapper<AccountBook> accountBookQueryWrapper = new QueryWrapper<>();
            accountBookQueryWrapper.eq("uuid", s.getAccountBookId());
            AccountBook accountBook = iAccountBookService.getOne(accountBookQueryWrapper);
            InformModel informModel = new InformModel();
            BeanUtils.copyProperties(s, informModel); //拷贝字段相同
            informModel.setUserA(userA);
            informModel.setUserB(userB);
            informModel.setBillTemp(billTemp);
            informModel.setAccountBook(accountBook);
            informModels.add(informModel);
        }
        return informModels;
    }

    /**
     * 查询根据发送者 查询通知
     *
     * @param inform
     * @return
     */
    @PostMapping("getUserAInforms")
    public List<Inform> getUserAInforms(@RequestBody Inform inform) {
        QueryWrapper<Inform> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_a", inform.getUserB());
        return iInformService.list(queryWrapper);
    }

    /**
     * 根据uuid删除通知
     *
     * @param informModel
     * @return
     */
    @PostMapping("deleteInform")
    public boolean deleteInform(@RequestBody InformModel informModel) {
        QueryWrapper<Inform> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid", informModel.getUuid());
        if (informModel.getType().equals(Inform.AUDIT_FAILED) || informModel.getType().equals(Inform.BE_APPROVED)) {
            String path = Util.BILL_PATH + informModel.getBillTemp().getImg();
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            QueryWrapper<BillTemp> billTempQueryWrapper = new QueryWrapper<>();
            billTempQueryWrapper.eq("uuid", informModel.getBillTemp().getUuid());
            iBillTempService.remove(billTempQueryWrapper); //删除临时账目
        }

        return iInformService.remove(queryWrapper);
    }


}
