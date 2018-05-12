package cn.edu.gdou.szxhcl.model.vo.homework;

public class HomeworkQueryVo {
    private String title;
    private String teacher;
    private Byte timeout;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Byte getTimeout() {
        return timeout;
    }

    public void setTimeout(Byte timeout) {
        this.timeout = timeout;
    }
}
