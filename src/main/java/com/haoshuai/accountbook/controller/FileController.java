package com.haoshuai.accountbook.controller;

import com.haoshuai.accountbook.entity.ImgList;
import com.haoshuai.accountbook.service.IImgListService;
import com.haoshuai.accountbook.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传
 */
@Controller
public class FileController {

    private String destPath = "D:\\TangHaoShuai\\Pictures\\vue_img\\Share\\ShareCR\\";// 生成的二维码的路径及名称


    @Autowired
    private IUserService iUserService;

    @Autowired
    private IImgListService iImgListService;


    /**
     * 文章图片集合
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
        String filePath = "D:\\TangHaoShuai\\Pictures\\vue_img\\article_img\\"; // 上传后的路径
        fileName = uuid + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs(); //路径没有就创建
        }
        try {
            file.transferTo(dest); //写入文件
            ImgList imgList = new ImgList();
            imgList.setUuid(UUID.randomUUID().toString().trim().replaceAll("-", ""));
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
