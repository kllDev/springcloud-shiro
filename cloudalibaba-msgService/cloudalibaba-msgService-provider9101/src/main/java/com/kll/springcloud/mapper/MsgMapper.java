package com.kll.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kll.springcloud.entities.Article;
import com.kll.springcloud.entities.ArticleDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MsgMapper extends BaseMapper<ArticleDetail> {

    @Select("select id,articleId,articleTitle,author,summary,createTime,categories from article LIMIT #{start},#{end}")
    List<Article> getArticle(@Param("start") Integer start, @Param("end") Integer end);

    @Select("select * from article where articleId=#{articleId}")
    ArticleDetail getArticleDetail(@Param("articleId") String articleId);

    @Insert(" insert into article(articleId,articleTitle,author,visits,summary,createTime,categories,content) " +
            " values (#{articleId},#{articleTitle},#{author},#{visits},#{summary},#{createTime},#{categories},#{content})")
    Integer publishArticle(ArticleDetail articleDetail);

    @Select("SELECT COUNT(id) FROM article")
    Integer getCount();

    @Select("select id from article where articleId=#{articleId}")
    Integer getArticleByArticleId(@Param("articleId") String articleId);


    @Select("SELECT COUNT(id) FROM article WHERE categories=#{categories}")
    Integer getCountByKey(@Param("categories") String categories);

    @Select("select id,articleId,articleTitle,author,summary,createTime,categories from article where author=#{author} LIMIT #{start},#{end}")
    List<Article> getArticleByAuthor(@Param("start") Integer start, @Param("end") Integer end, @Param("author") String author);

    @Select("select id,articleId,articleTitle,author,summary,createTime,categories from article where categories=#{categories} LIMIT #{start},#{end}")
    List<Article> getArticleByKey(@Param("start") Integer start, @Param("end") Integer end, @Param("categories") String categories);

}
