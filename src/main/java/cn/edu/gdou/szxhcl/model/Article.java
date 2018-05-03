package cn.edu.gdou.szxhcl.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, length = 32)
    private String id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Lob
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private Date createDt;
    @Column(nullable = false)
    private Date updateDt;
    @Column(nullable = false)
    private Date sortDt;
    @ManyToOne
    @JoinColumn(name="article_class_id")
    private ArticleClass articleClass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    public Date getSortDt() {
        return sortDt;
    }

    public void setSortDt(Date sortDt) {
        this.sortDt = sortDt;
    }

    public ArticleClass getArticleClass() {
        return articleClass;
    }

    public void setArticleClass(ArticleClass articleClass) {
        this.articleClass = articleClass;
    }
}
