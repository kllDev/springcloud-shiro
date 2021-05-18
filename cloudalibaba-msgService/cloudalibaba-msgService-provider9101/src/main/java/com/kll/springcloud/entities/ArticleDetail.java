package com.kll.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
    @AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "articlees", type = "ArticleDetail")
public class ArticleDetail implements Serializable {
    @Id
    private Integer id;
    private String articleId;
    private String articleTitle;
    private String author;
    private String visits;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String summary;
    private String type;
    private String createTime;
    private String categories;
    private Integer lastArticleId;
    private Integer nextArticleId;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

    public ArticleDetail(String articleTitle, String author, String summary, String type, String categories, String content) {
        this.articleTitle = articleTitle;
        this.author = author;
        this.summary = summary;
        this.type = type;
        this.categories = categories;
        this.content = content;
    }


}

