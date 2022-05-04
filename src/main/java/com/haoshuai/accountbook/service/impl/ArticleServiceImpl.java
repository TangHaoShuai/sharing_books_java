package com.haoshuai.accountbook.service.impl;

import com.haoshuai.accountbook.entity.Article;
import com.haoshuai.accountbook.mapper.ArticleMapper;
import com.haoshuai.accountbook.service.IArticleService;
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
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
