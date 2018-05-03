package cn.edu.gdou.szxhcl.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "news_class")
public class NewsClass {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, length = 32)
    private String id;
    @Column(nullable = false)
    private String className;
    @Column(nullable = false)
    private Date sortDt;
    @Column(nullable = false)
    private String banner;
    @OneToMany(cascade={CascadeType.REMOVE},mappedBy = "newsClass",fetch = FetchType.LAZY)
    private List<News> newsList;

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

    public Date getSortDt() {
        return sortDt;
    }

    public void setSortDt(Date sortDt) {
        this.sortDt = sortDt;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}
