package com.kll.springcloud.service;

import com.kll.springcloud.entities.ArticleDetail;

import java.util.List;

public interface EsService {

    Iterable<ArticleDetail> findAll();

    void save(List<ArticleDetail> list);

    void save(ArticleDetail bean);

    List<ArticleDetail> findListBySummary(String summary);

    List<ArticleDetail> findListByContent(String content);
}
