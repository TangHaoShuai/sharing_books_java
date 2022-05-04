package com.haoshuai.accountbook.entity.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 账目饼图实体类
 */
@Data
public class BillPieData implements Serializable {

    private List<SeriesBeanA> series;

    @Data
    public static class SeriesBeanA implements Serializable {
        private List<DataBean> data;

        @Data
        public static class DataBean implements Serializable {
            /**
             * name : 食品饮料
             * value : 50
             */

            private String name;
            private Double value;
        }
    }
}
