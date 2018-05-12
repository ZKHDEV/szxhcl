package cn.edu.gdou.szxhcl.model.vo.comment;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class CommentVo implements Comparable {
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
    private String updateDt;
    private Integer replyNum;
    private List<CommentVo> chiCommList;

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

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public List<CommentVo> getChiCommList() {
        return chiCommList;
    }

    public void setChiCommList(List<CommentVo> chiCommList) {
        this.chiCommList = chiCommList;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    @Override
    public int compareTo(Object o) {
        CommentVo commentVo = (CommentVo)o;
        return commentVo.getCreateDt().compareTo(this.getCreateDt());
    }
}
