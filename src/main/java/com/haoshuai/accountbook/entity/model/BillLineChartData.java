package com.haoshuai.accountbook.entity.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BillLineChartData implements Serializable {

    private List<String> categories;
    private List<SeriesBean> series;

    @Data
    public static class SeriesBean implements Serializable {
        /**
         * name : 支出
         * data : [35,8,25,37,4,20,35,8,25,37,4,20]
         */

        private String name;
        private List<Double> data;
    }
}
