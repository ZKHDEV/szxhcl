package cn.edu.gdou.szxhcl.model.vo.slideshow;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class SlideshowVo {
    private String id;
    @NotEmpty(message = "必填")
    @Length(max = 32, message = "长度须小于32个字节")
    private String title;
    private String url;
    private Boolean showed;
    private String createDt;
    private String updateDt;
    private String sortDt;

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
}
