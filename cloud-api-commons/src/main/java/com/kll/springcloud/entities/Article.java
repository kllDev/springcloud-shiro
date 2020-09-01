package com.kll.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private Integer id;
    private String articleId;
    private String articleTitle;
    private String author;
    private String summary;
    private String createTime;
    private String categories;
}
