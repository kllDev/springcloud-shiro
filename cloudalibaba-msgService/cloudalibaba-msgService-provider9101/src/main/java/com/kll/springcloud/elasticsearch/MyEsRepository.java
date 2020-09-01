package com.kll.springcloud.elasticsearch;


import com.kll.springcloud.entities.ArticleDetail;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyEsRepository extends ElasticsearchRepository<ArticleDetail,String> {

}
