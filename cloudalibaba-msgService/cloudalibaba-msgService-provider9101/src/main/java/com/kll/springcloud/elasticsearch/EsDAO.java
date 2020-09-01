package com.kll.springcloud.elasticsearch;

import com.kll.springcloud.entities.ArticleDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EsDAO extends CrudRepository<ArticleDetail, Long> {
    List<ArticleDetail> findArticleDetailBySummary(String summary);
    List<ArticleDetail> findArticleDetailByContent(String content);

}
