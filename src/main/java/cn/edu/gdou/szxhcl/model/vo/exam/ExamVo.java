package cn.edu.gdou.szxhcl.model.vo.exam;

import cn.edu.gdou.szxhcl.model.Choice;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class ExamVo {
    private String id;
    @NotEmpty(message = "必填")
    private String title;
    private String createDt;
    private String updateDt;
    private String remarks;
    private String userId;
    private String username;
    private Integer total;
    private List<ChoiceVo> choiceList;

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<ChoiceVo> getChoiceList() {
        return choiceList;
    }

    public void setChoiceList(List<ChoiceVo> choiceList) {
        this.choiceList = choiceList;
    }
}
