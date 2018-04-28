package cn.edu.gdou.szxhcl.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "slideshow")
public class Slideshow {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, length = 32)
    private String id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private Boolean showed;
    @Column(nullable = false)
    private Date createDt;
    @Column(nullable = false)
    private Date updateDt;
    @Column(nullable = false)
    private Date sortDt;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getShowed() {
        return showed;
    }

    public void setShowed(Boolean showed) {
        this.showed = showed;
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
}
