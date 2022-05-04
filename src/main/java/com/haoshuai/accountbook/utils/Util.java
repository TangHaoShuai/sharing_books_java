package com.haoshuai.accountbook.utils;

import java.util.UUID;

public class Util {
    public final static String BILL_PATH = "D:\\TangHaoShuai\\SharingBooks\\static\\bill_img\\"; //账目图片路径
    public final static String USER_PATH = "D:\\TangHaoShuai\\SharingBooks\\static\\user_tx\\";  //用户头像路径
    public final static String ARTICLE_IMG = "D:\\TangHaoShuai\\SharingBooks\\static\\article_img\\";  //文章图片路径

    public static String getUUID() {
        return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }

    public static boolean isStringNull(String val) {
        if (val == null || val == "") {
            return false;
        } else {
            return true;
        }
    }
}
