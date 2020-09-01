package com.kll.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetail {
    private Integer id;
    private String articleId;
    private String articleTitle;
    private String author;
    private String visits;
    private String summary;
    private String type;
    private String createTime;
    private String categories;
    private Integer lastArticleId;
    private Integer nextArticleId;
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
