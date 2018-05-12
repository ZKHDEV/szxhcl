package cn.edu.gdou.szxhcl.model.vo.user;

import org.hibernate.validator.constraints.NotEmpty;

public class UserPsdVo {
    private String id;
    @NotEmpty(message = "必填")
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
