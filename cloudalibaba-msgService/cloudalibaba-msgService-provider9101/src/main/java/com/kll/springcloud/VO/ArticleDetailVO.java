package com.kll.springcloud.VO;

import java.util.Objects;

public class ArticleDetailVO {
    private String articleTitle;
    private String author;
    private String summary;
    private String type;
    private String categories;
    private String content;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleDetailVO that = (ArticleDetailVO) o;
        return Objects.equals(articleTitle, that.articleTitle) &&
                Objects.equals(author, that.author) &&
                Objects.equals(summary, that.summary) &&
                Objects.equals(type, that.type) &&
                Objects.equals(categories, that.categories) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleTitle, author, summary, type, categories, content);
    }

    @Override
    public String toString() {
        return "ArticleDetailVO{" +
                "articleTitle='" + articleTitle + '\'' +
                ", author='" + author + '\'' +
                ", summary='" + summary + '\'' +
                ", type='" + type + '\'' +
                ", categories='" + categories + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArticleDetailVO() {
    }

    public ArticleDetailVO(String articleTitle, String author, String summary, String type, String categories, String content) {
        this.articleTitle = articleTitle;
        this.author = author;
        this.summary = summary;
        this.type = type;
        this.categories = categories;
        this.content = content;
    }
}
