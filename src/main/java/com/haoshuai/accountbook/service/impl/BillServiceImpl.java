package com.haoshuai.accountbook.service.impl;

import com.haoshuai.accountbook.entity.Bill;
import com.haoshuai.accountbook.mapper.BillMapper;
import com.haoshuai.accountbook.service.IBillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-04-19
 */
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements IBillService {

}
