package com.haoshuai.accountbook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haoshuai.accountbook.entity.Praise;
import com.haoshuai.accountbook.entity.User;
import com.haoshuai.accountbook.entity.model.PraiseModel;
import com.haoshuai.accountbook.service.IPraiseService;
import com.haoshuai.accountbook.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * 点赞
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-03-08
 */
@RestController
@RequestMapping("/praise")
public class PraiseController {

    @Autowired
    private IPraiseService iPraiseService;

    @Autowired
    private IUserService iUserService;

    /**
     * 根据文章ID和点赞者ID 获取点赞集合
     *
     * @param praise
     * @return
     */
    @PostMapping("getPraises")
    public List<PraiseModel> getPraises(@RequestBody Praise praise) {
        List<PraiseModel> praiseModels = new ArrayList<>();
        if (praise.getArticleid() != null && praise.getArticleid() != "" && praise.getUserid() != null && praise.getUserid() != "") {
            QueryWrapper<Praise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userid", praise.getUserid());
            queryWrapper.eq("articleid", praise.getArticleid());
            List<Praise> praiseList = iPraiseService.list(queryWrapper);

            for (Praise p : praiseList) {
                PraiseModel praiseModel = new PraiseModel();
                praiseModel.setUserid(p.getUserid());
                praiseModel.setArticleid(p.getArticleid());
                praiseModel.setUuid(p.getUuid());
                praiseModel.setDate(p.getDate());
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("phone", p.getUserid());
                User user1 = iUserService.getOne(userQueryWrapper);
                praiseModel.setUser(user1);
                praiseModels.add(praiseModel);
            }
            return praiseModels;
        }
        return null;
    }


//    @PostMapping("getPraises")
//    public List<Praise> getPraises() {
//        List list = iPraiseService.list();
//        return list;
//    }

    /**
     * 删除点赞
     *
     * @param praise
     * @return
     */
    @PostMapping("deleteParam")
    public boolean deleteParam(@RequestBody Praise praise) {
        if (praise.getUuid() != null && praise.getUuid() != "") {
            QueryWrapper<Praise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid", praise.getUuid());
            return iPraiseService.remove(queryWrapper);
        }
        return false;
    }

    /**
     * 根据文章ID和点赞者ID 获取点赞集合
     *
     * @param praise
     * @return
     */
    @PostMapping("getPathParam")
    public Praise getPathParam(@RequestBody Praise praise) {
        QueryWrapper<Praise> praiseQueryWrapper = new QueryWrapper<>();
        if (praise.getArticleid() == null || praise.getArticleid().equals("") || praise.getUserid().equals("") || praise.getUserid() == null) {
            return null;
        }
        praiseQueryWrapper.eq("articleid", praise.getArticleid());
        praiseQueryWrapper.eq("userid", praise.getUserid());
        return iPraiseService.getOne(praiseQueryWrapper);
    }

    /**
     * 添加点赞数据
     *
     * @param praise
     * @return
     */
    @PostMapping("addPraise")
    public String addPraise(@RequestBody Praise praise) {
        if (praise.getArticleid() == null || praise.getArticleid().equals("") || praise.getUserid().equals("") || praise.getUserid() == null) {
            System.out.println("数据不符合规范" + praise.toString());
            return "500";
        } else {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            praise.setDate(df.format(new Date()));
            String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
            praise.setUuid(uuid);
            iPraiseService.save(praise);
            return "200";
        }
    }

    /**
     * 删除点赞
     *
     * @param praise
     * @return
     */
    @PostMapping("deletePraise")
    public String deletePraise(@RequestBody Praise praise) {
        QueryWrapper<Praise> praiseQueryWrapper = new QueryWrapper<>();
        praiseQueryWrapper.eq("articleid", praise.getArticleid());
        praiseQueryWrapper.eq("userid", praise.getUserid());
        return iPraiseService.remove(praiseQueryWrapper) ? "200" : "500";
    }
}
