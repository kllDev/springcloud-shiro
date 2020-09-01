package com.kll.springcloud.service;

import com.kll.springcloud.entities.Article;
import com.kll.springcloud.entities.ArticleDetail;
import com.kll.springcloud.entities.CommonResult;
import com.kll.springcloud.entities.UserLogin;
import org.bouncycastle.util.Integers;

import java.util.List;
import java.util.Set;

public interface MsgService {

    List<Article> getArticle(Integer start, Integer end);

    ArticleDetail getArticleDetail(String articleId);


    Integer publishArticle(ArticleDetail articleDetail);

    List<Article> getArticleByAuthor(Integer start, Integer end, String author);

    List<Article> getArticleByKey(Integer start, Integer end, String key);

    Integer getCount();

    Integer getCountByKey(String key);

}
