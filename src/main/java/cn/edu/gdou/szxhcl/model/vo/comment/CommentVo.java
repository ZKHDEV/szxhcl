package cn.edu.gdou.szxhcl.model.vo.comment;

import org.hibernate.validator.constraints.NotEmpty;

public class CommentVo {
    private String id;
    @NotEmpty(message = "必填")
    private String title;
    @NotEmpty(message = "必填")
    private String author;
    @NotEmpty(message = "必填")
    private String email;
    @NotEmpty(message = "必填")
    private String content;
    private Boolean top;
    private String createDt;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }
}
