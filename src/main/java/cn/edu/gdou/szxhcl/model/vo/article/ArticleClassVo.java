package cn.edu.gdou.szxhcl.model.vo.article;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class ArticleClassVo {
    private String id;
    @NotEmpty(message = "必填")
    @Length(max = 32, message = "长度须小于32个字节")
    private String className;
    private String sortDt;
    private List<ArticleVo> articleList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSortDt() {
        return sortDt;
    }

    public void setSortDt(String sortDt) {
        this.sortDt = sortDt;
    }

    public List<ArticleVo> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<ArticleVo> articleList) {
        this.articleList = articleList;
    }
}
