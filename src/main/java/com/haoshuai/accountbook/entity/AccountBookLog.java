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
 * @since 2022-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AccountBookLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;

    private String message;

    private String date;

    /**
     * 账本ID
     */
    private String accountBookId;
}
