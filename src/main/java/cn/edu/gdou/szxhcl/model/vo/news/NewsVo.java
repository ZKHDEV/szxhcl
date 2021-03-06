package cn.edu.gdou.szxhcl.model.vo.news;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class NewsVo implements Comparable {
    private String id;
    @NotEmpty(message = "必填")
    @Length(max = 32, message = "长度须小于32个字节")
    private String title;
    @NotNull(message = "必填")
    private Byte source;
    @NotEmpty(message = "必填")
    private String author;
    @NotEmpty(message = "必填")
    private String content;
    private String createDt;
    private String updateDt;
    private String sortDt;
    @NotEmpty(message = "必填")
    private String classId;
    private String className;

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

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public String getSortDt() {
        return sortDt;
    }

    public void setSortDt(String sortDt) {
        this.sortDt = sortDt;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public int compareTo(Object o) {
        NewsVo newsVo = (NewsVo)o;
        return newsVo.getSortDt().compareTo(this.getSortDt());
    }
}
