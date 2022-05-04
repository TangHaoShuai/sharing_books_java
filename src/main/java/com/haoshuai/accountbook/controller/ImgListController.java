package com.haoshuai.accountbook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.haoshuai.accountbook.entity.Article;
import com.haoshuai.accountbook.entity.ImgList;
import com.haoshuai.accountbook.service.IImgListService;
import com.haoshuai.accountbook.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *     帖子图片
 * 前端控制器
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-03-04
 */
@RestController
@RequestMapping("/img-list")
public class ImgListController {
    @Autowired
    private IImgListService iImgListService;

    /**
     * 获取图片集合
     * @return
     */
    @PostMapping("getImgList")
    public List<ImgList> getList() {
        return iImgListService.list();
    }

    /**
     * 根据文章ID 获取图片集合
     * @param article
     * @return
     */
    @PostMapping("getByUserImgList")
    public List<ImgList> getByUserImgList(@RequestBody Article article) {
        QueryWrapper<ImgList> wrapper = new QueryWrapper<>();
        wrapper.eq("imgid", article.getImgid());
        return iImgListService.list(wrapper);
    }

    /**
     * 更新图片信息
     * @param imgList
     */
    @PostMapping("upImgList")
    public void upImgList(ImgList imgList) {
        UpdateWrapper<ImgList> wrapper = new UpdateWrapper<>();
        wrapper.eq("uuid", imgList.getUuid());
        iImgListService.update(imgList, wrapper);
    }

    /**
     * 删除图片
     * @param imgList
     * @return
     */
    @PostMapping("deImgList")
    public boolean deImgList(@RequestBody  ImgList imgList) {
        if (imgList.getUuid() == null && imgList.getUuid() == ""){
            return false;
        }
        QueryWrapper<ImgList> wrapper = new QueryWrapper<>();
        wrapper.eq("uuid", imgList.getUuid());
        String filePath = Util.ARTICLE_IMG; // 上传后的路径
        imgList = iImgListService.getOne(wrapper);
        String tempUrl = filePath + imgList.getImgUrl();
        File file = new File(tempUrl);
        if (file.exists()) {
            file.delete();
        } else {
            return false;
        }
        return iImgListService.remove(wrapper);
    }

    /**
     * 上传文章图片
     *
     * @param file    图片
     * @param imgID   对应文章那边imgID 由前端传过来
     * @param model
     * @param request
     * @return
     */
    @PostMapping(value = "/imgFileUpload")
    @ResponseBody
    public String imgFileUpload(@RequestParam(value = "file") MultipartFile file, @RequestParam("imgID") String imgID, Model model, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = Util.ARTICLE_IMG; // 上传后的路径
        fileName = uuid + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs(); //路径没有就创建
        }
        try {
            file.transferTo(dest); //写入文件
            ImgList imgList = new ImgList();
            imgList.setUuid(Util.getUUID());
            imgList.setImgid(imgID); // 对应文章里面的图片ID
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            imgList.setDate(df.format(new Date()));
            imgList.setImgUrl(fileName); // 图片路径
            iImgListService.save(imgList);
        } catch (Exception e) {
            e.printStackTrace();
            return "err";
        }
        return "succeed";
    }

}
