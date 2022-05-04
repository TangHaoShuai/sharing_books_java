package com.haoshuai.accountbook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.haoshuai.accountbook.entity.BillTemp;
import com.haoshuai.accountbook.service.IBillTempService;
import com.haoshuai.accountbook.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 临时账单
 * 前端控制器
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/bill-temp")
public class BillTempController {

    @Autowired
    private IBillTempService iBillTempService;


    /**
     * 根据ID 删除临时账单
     *
     * @param billTemp
     * @return
     */
    @PostMapping("deleteBillTemp")
    public boolean deleteBillTemp(@RequestBody BillTemp billTemp) {
        if (Util.isStringNull(billTemp.getUuid())) {
            QueryWrapper<BillTemp> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid", billTemp.getUuid());
            return iBillTempService.remove(queryWrapper);
        }
        return false;
    }

    /**
     * 根据ID 查询临时账单
     *
     * @param billTemp
     * @return
     */
    @PostMapping("getBillTemp")
    public BillTemp getBillTemp(@RequestBody BillTemp billTemp) {
        if (Util.isStringNull(billTemp.getUuid())) {
            QueryWrapper<BillTemp> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid", billTemp.getUuid());
            return iBillTempService.getOne(queryWrapper);
        }
        return null;
    }

    /**
     * 添加临时账单
     *
     * @param billTemp
     * @return
     */
    @PostMapping("addBill")
    public Map<String, Object> addBill(@RequestBody BillTemp billTemp) {
        Map<String, Object> errMap = new HashMap<>();
        errMap.put("succeed", 500);
        if (Util.isStringNull(billTemp.getAccountBookId()) && Util.isStringNull(billTemp.getAccountBookUser()) && billTemp.getMoney() != 0) {
            billTemp.setUuid(Util.getUUID());
            if (billTemp.getDate() == "" || billTemp.getDate() == null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
                billTemp.setDate(df.format(new Date()));
            }
            Map<String, Object> map = new HashMap<>();
            map.put("succeed", 200);
            map.put("message", billTemp.getUuid());
            errMap.put("message", "保存失败");
            return iBillTempService.save(billTemp) ? map : errMap;
        }
        errMap.put("message", "数据为空");
        return errMap;
    }

    /**
     * 图片上传
     *
     * @param file
     * @param uuid    账单ID
     * @param model
     * @param request
     * @return
     */
    @PostMapping(value = "/imgUpload")
    @ResponseBody
    public String imgFileUpload(@RequestParam(value = "file") MultipartFile file,
                                @RequestParam("uuid") String uuid,
                                Model model, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = Util.BILL_PATH; // 上传后的路径
        Long startTs = System.currentTimeMillis();//时间戳
        fileName = startTs + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs(); //路径没有就创建
        }
        try {
            file.transferTo(dest); //写入文件
            UpdateWrapper<BillTemp> billTempUpdateWrapper = new UpdateWrapper<>();
            billTempUpdateWrapper.eq("uuid", uuid);
            billTempUpdateWrapper.set("img", fileName);
            iBillTempService.update(null, billTempUpdateWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return "err";
        }
        return "succeed";
    }


}
