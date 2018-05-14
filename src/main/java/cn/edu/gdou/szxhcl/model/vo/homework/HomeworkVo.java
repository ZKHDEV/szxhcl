package cn.edu.gdou.szxhcl.model.vo.homework;

import cn.edu.gdou.szxhcl.model.vo.user.UserVo;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class HomeworkVo {
    private String id;
    @NotEmpty(message = "必填")
    private String title;
    @NotEmpty(message = "必填")
    private String content;
    private String createDt;
    private String updateDt;
    private String endDt;
    private Boolean timeout;
    private Integer submitNum;
    private UserVo user;
    private List<HomeworkSubmitVo> homeworkSubmitList;
    private HomeworkSubmitVo homeworkSubmit;

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

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public Boolean getTimeout() {
        return timeout;
    }

    public void setTimeout(Boolean timeout) {
        this.timeout = timeout;
    }

    public Integer getSubmitNum() {
        return submitNum;
    }

    public void setSubmitNum(Integer submitNum) {
        this.submitNum = submitNum;
    }

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }

    public List<HomeworkSubmitVo> getHomeworkSubmitList() {
        return homeworkSubmitList;
    }

    public void setHomeworkSubmitList(List<HomeworkSubmitVo> homeworkSubmitList) {
        this.homeworkSubmitList = homeworkSubmitList;
    }

    public HomeworkSubmitVo getHomeworkSubmit() {
        return homeworkSubmit;
    }

    public void setHomeworkSubmit(HomeworkSubmitVo homeworkSubmit) {
        this.homeworkSubmit = homeworkSubmit;
    }
}
