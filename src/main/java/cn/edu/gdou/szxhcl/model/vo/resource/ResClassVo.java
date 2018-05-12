package cn.edu.gdou.szxhcl.model.vo.resource;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class ResClassVo {
    private String id;
    @NotEmpty(message = "必填")
    @Length(max = 32, message = "长度须小于32个字节")
    private String className;
    private List<ResourceVo> resList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<ResourceVo> getResList() {
        return resList;
    }

    public void setResList(List<ResourceVo> resList) {
        this.resList = resList;
    }
}
