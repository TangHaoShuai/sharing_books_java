package com.haoshuai.accountbook.service.impl;

import com.haoshuai.accountbook.entity.Comment;
import com.haoshuai.accountbook.mapper.CommentMapper;
import com.haoshuai.accountbook.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-05-03
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
