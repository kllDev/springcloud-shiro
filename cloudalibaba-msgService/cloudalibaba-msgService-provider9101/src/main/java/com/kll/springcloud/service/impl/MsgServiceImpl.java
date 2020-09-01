package com.kll.springcloud.service.impl;

import com.kll.springcloud.entities.*;
import com.kll.springcloud.mapper.MsgMapper;
import com.kll.springcloud.service.MsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class MsgServiceImpl implements MsgService {
    @Autowired
    private MsgMapper msgMapper;

    @Override
    public List<Article> getArticle(Integer start, Integer end) {
        return msgMapper.getArticle(start,end);
    }

    @Override
    public ArticleDetail getArticleDetail(String articleId) {
        return msgMapper.getArticleDetail(articleId);
    }

    @Override
    public Integer publishArticle(ArticleDetail articleDetail) {
        msgMapper.publishArticle(articleDetail);
        return msgMapper.getArticleByArticleId(articleDetail.getArticleId());
    }

    @Override
    public List<Article> getArticleByAuthor(Integer start, Integer end, String author) {
        return msgMapper.getArticleByAuthor(start,end,author);
    }

    @Override
    public List<Article> getArticleByKey(Integer start, Integer end, String key) {
        return msgMapper.getArticleByKey(start,end,key);
    }

    @Override
    public Integer getCount() {
        return msgMapper.getCount();
    }

    @Override
    public Integer getCountByKey(String key) {
        return msgMapper.getCountByKey(key);
    }
}
