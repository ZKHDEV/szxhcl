package cn.edu.gdou.szxhcl.model.vo.exam;

import org.hibernate.validator.constraints.NotEmpty;

public class ChoiceVo {
    private String id;
    @NotEmpty(message = "必填")
    private String title;
    private Integer no;
    private String createDt;
    private String updateDt;
    private String sortDt;
    private String remarks;
    private String userId;
    private String username;
    @NotEmpty(message = "必填")
    private String optionA;
    @NotEmpty(message = "必填")
    private String optionB;
    @NotEmpty(message = "必填")
    private String optionC;
    @NotEmpty(message = "必填")
    private String optionD;
    @NotEmpty(message = "必填")
    private String answer;
    @NotEmpty(message = "必填")
    private String examId;
    private String examTitle;
    private String userAns;
    private String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
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

    public String getSortDt() {
        return sortDt;
    }

    public void setSortDt(String sortDt) {
        this.sortDt = sortDt;
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

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public String getUserAns() {
        return userAns;
    }

    public void setUserAns(String userAns) {
        this.userAns = userAns;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
