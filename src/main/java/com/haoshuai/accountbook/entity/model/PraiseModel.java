package com.haoshuai.accountbook.entity.model;

import com.haoshuai.accountbook.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PraiseModel {

    private String articleid;

    private String userid;

    private User user;

    private String date;

    private String uuid;


}
