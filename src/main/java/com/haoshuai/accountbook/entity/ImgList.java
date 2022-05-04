package com.haoshuai.accountbook.entity;

    import java.io.Serializable;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 
    * </p>
*
* @author TangHaoShuai
* @since 2022-05-03
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class ImgList implements Serializable {

    private static final long serialVersionUID = 1L;

    private String imgid;

    private String imgUrl;

    private String date;

    private String imgDescribe;

    private String uuid;


}
