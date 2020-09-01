package com.kll.springcloud.service.impl;

import com.kll.springcloud.elasticsearch.EsDAO;
import com.kll.springcloud.elasticsearch.MsgPOJO;
import com.kll.springcloud.entities.ArticleDetail;
import com.kll.springcloud.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class EsServiceImpl implements EsService {
    @Autowired
    EsDAO testDao;

    @Override
    public Iterable<ArticleDetail> findAll() {

        return testDao.findAll();
    }

    @Override
    public void save(List<ArticleDetail> list) {
        testDao.saveAll(list);
    }

    @Override
    public void save(ArticleDetail bean) {
        testDao.save(bean);
    }

    @Override
    public List<ArticleDetail> findListBySummary(String summary) {
        return testDao.findArticleDetailBySummary(summary);
    }



    @Override
    public List<ArticleDetail> findListByContent(String content) {
        return testDao.findArticleDetailByContent(content);
    }
}
