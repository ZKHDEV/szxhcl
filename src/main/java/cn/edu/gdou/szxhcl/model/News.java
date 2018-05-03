package cn.edu.gdou.szxhcl.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, length = 32)
    private String id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Byte source;
    @Column(nullable = false)
    private String author;
    @Lob
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private Date sortDt;
    @Column(nullable = false)
    private Date createDt;
    @Column(nullable = false)
    private Date updateDt;
    @ManyToOne
    @JoinColumn(name="news_class_id")
    private NewsClass newsClass;

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

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
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

    public Date getSortDt() {
        return sortDt;
    }

    public void setSortDt(Date sortDt) {
        this.sortDt = sortDt;
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

    public NewsClass getNewsClass() {
        return newsClass;
    }

    public void setNewsClass(NewsClass newsClass) {
        this.newsClass = newsClass;
    }
}
