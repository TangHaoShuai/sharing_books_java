package com.haoshuai.accountbook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.haoshuai.accountbook.entity.User;
import com.haoshuai.accountbook.service.IUserService;
import com.haoshuai.accountbook.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 用户
 * 前端控制器
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;


    @PostMapping("login")
    public User login(@RequestBody User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", user.getPhone());
        queryWrapper.eq("password", user.getPassword());
        return iUserService.getOne(queryWrapper);
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @PostMapping("addUser")
    public boolean addUser(@RequestBody User user) {
        if (user.getPhone() != "" && user.getPhone() != null) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone", user.getPhone());
            User user_temp = iUserService.getOne(queryWrapper);
            if (user_temp != null) {
                //用户存在 返回假
                return false;
            }
        } else {
            return false;
        }
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        user.setUuid(uuid);
        return iUserService.save(user);
    }

    /**
     * @param user
     * @return user
     * 查询一个user
     */
    @PostMapping("getUser")
    public User getUser(@RequestBody User user) {
        //构建条件构造器
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        //条件根据手机号来查
        userQueryWrapper.eq("phone", user.getPhone());
        User user_type = iUserService.getOne(userQueryWrapper);
        return user_type;
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @PostMapping("getUsers")
    public List<User> getUsers() {
        return iUserService.list();
    }

    /**
     * 根据手机号 删除用户
     *
     * @param user
     * @return
     */
    @PostMapping("deleteUser")
    public boolean deleteUser(@RequestBody User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("phone", user.getPhone());
        return iUserService.remove(userQueryWrapper);
    }

    /**
     * 根据手机号跟新用户数据
     *
     * @param user
     * @return
     */
    @PostMapping("updateUser")
    public boolean updateUser(@RequestBody User user) {
        if (Util.isStringNull(user.getUuid())){
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("uuid", user.getUuid());
            return iUserService.update(user, userQueryWrapper);
        }else {
            return false;
        }
    }

    /**
     * 头像上传
     *
     * @param file
     * @param id      用户id
     * @param imgId   前端传过来的imgId  图片存在删除 更换新头像
     * @param model
     * @param request
     * @return
     */
    @PostMapping(value = "/headPortraitUpload")
    @ResponseBody
    public String headPortraitUpload(@RequestParam(value = "file") MultipartFile file, @RequestParam("id") String id, @RequestParam("imgId") String imgId, Model model, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }

        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = Util.USER_PATH; // 上传后的路径
        Long startTs = System.currentTimeMillis();//时间戳
        fileName = id + startTs + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        if (!imgId.equals("tx.png")){
            String tempPath = filePath + imgId;
            File file1 = new File(tempPath);
            if (file1.exists() ) {
                try {
                    file1.delete();
                } catch (Exception e) {
                    System.out.println("文件删除错误！" + e.getMessage());
                }
            }
        }
        try {
            file.transferTo(dest);
            UpdateWrapper<User> userWrapper = new UpdateWrapper<>();
            userWrapper.eq("phone", id);
            userWrapper.set("url", fileName);
            iUserService.update(null, userWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "succeed";
    }

}
