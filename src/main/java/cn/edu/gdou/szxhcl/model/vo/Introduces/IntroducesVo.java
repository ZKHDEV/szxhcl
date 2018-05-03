package cn.edu.gdou.szxhcl.model.vo.Introduces;

import org.hibernate.validator.constraints.NotEmpty;

public class IntroducesVo {
    private String id;
    @NotEmpty(message = "必填")
    private String content;
    private String updateDt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }
}
