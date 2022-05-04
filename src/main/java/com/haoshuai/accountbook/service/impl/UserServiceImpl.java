package com.haoshuai.accountbook.service.impl;

import com.haoshuai.accountbook.entity.User;
import com.haoshuai.accountbook.mapper.UserMapper;
import com.haoshuai.accountbook.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
