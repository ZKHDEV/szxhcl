package cn.edu.gdou.szxhcl.model.vo.news;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class NewsClassVo {
    private String id;
    @NotEmpty(message = "必填")
    @Length(max = 32, message = "长度须小于32个字节")
    private String className;
    @NotEmpty(message = "必填")
    private String banner;
    private String sortDt;
    private List<NewsVo> newsList;

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

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public List<NewsVo> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewsVo> newsList) {
        this.newsList = newsList;
    }

    public String getSortDt() {
        return sortDt;
    }

    public void setSortDt(String sortDt) {
        this.sortDt = sortDt;
    }
}
