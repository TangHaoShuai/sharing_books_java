package com.haoshuai.accountbook;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SpringBootTest
class SharingBooksJavaTests {


    @Test
    void contextLoads() {
        List<String>list = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
        for (int i = 0; i < 7; i++) {
            Calendar calendar = Calendar.getInstance(); //创建Calendar 的实例
            calendar.add(Calendar.DAY_OF_MONTH, (-6+i)); //当前时间减去一天，即一天前的时间
            list.add(df.format(calendar.getTime()));
        }
        System.out.println(list.toString());
    }

}
