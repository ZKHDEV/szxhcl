package cn.edu.gdou.szxhcl.model.vo.link;

import org.hibernate.validator.constraints.NotEmpty;

public class LinkVo {
    private String id;
    @NotEmpty(message = "必填")
    private String title;
    @NotEmpty(message = "必填")
    private String url;

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
}
